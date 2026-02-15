#!/bin/bash
cd "$(dirname "$0")"

run_test() {
  local lanfile="$1"
  local instfile="$2"
  local in_content=""
  local out_expected=""
  local section=""
  
  while IFS= read -r line || [ -n "$line" ]; do
    if [ "$line" = "---in----" ]; then
      section="in"
      in_content=""
    elif [ "$line" = "---out---" ]; then
      section="out"
      out_expected=""
    elif [ "$section" = "in" ]; then
      [ -n "$in_content" ] && in_content="$in_content"$'\n'
      in_content="$in_content$line"
    elif [ "$section" = "out" ]; then
      [ -n "$out_expected" ] && out_expected="$out_expected"$'\n'
      out_expected="$out_expected$line"
    fi
  done < "$instfile"
  
  actual=$(printf '%s' "$in_content" | java -cp .:tools/java-cup-11b-runtime.jar Lang2Compiler -i "$lanfile" 2>&1)
  if [ "$actual" = "$out_expected" ]; then
    echo "OK: $lanfile"
    return 0
  else
    echo "FALHOU: $lanfile"
    echo "  Esperado:"
    printf '%s' "$out_expected" | sed 's/^/    /'
    echo ""
    echo "  Obtido:"
    printf '%s' "$actual" | sed 's/^/    /'
    echo ""
    return 1
  fi
}

echo "=== TESTES SEMANTICA/CERTO ==="
ok=0
fail=0
for lanfile in semantica/certo/simple/*.lan semantica/certo/function/*.lan semantica/certo/full/*.lan; do
  [ -f "$lanfile" ] || continue
  instfile="${lanfile%.lan}.inst"
  if [ -f "$instfile" ]; then
    if run_test "$lanfile" "$instfile"; then
      ok=$((ok+1))
    else
      fail=$((fail+1))
    fi
  else
    echo "AVISO: $lanfile sem .inst, executando interpretação..."
    out=$(java -cp .:tools/java-cup-11b-runtime.jar Lang2Compiler -i "$lanfile" 2>&1)
    if [ $? -eq 0 ]; then
      echo "OK (exec): $lanfile"
      ok=$((ok+1))
    else
      echo "FALHOU (exec): $lanfile"
      echo "$out" | head -5
      fail=$((fail+1))
    fi
  fi
done
echo ""
echo "Semantica: $ok OK, $fail falharam"
