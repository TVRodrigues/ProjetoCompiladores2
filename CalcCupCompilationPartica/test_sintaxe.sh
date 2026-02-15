#!/bin/bash
cd "$(dirname "$0")"

echo "=== TESTES SINTAXE/CERTO (esperado: accepted) ==="
ok=0
fail=0
for f in sintaxe/certo/*.lan sintaxe/certo/*.cmd; do
  [ -f "$f" ] || continue
  out=$(java -cp .:tools/java-cup-11b-runtime.jar Lang2Compiler -syn "$f" 2>&1)
  result=$(echo "$out" | tail -1)
  if [ "$result" = "accepted" ]; then
    echo "OK: $f"
    ok=$((ok+1))
  else
    echo "FALHOU: $f -> $result"
    fail=$((fail+1))
  fi
done
echo "Certo: $ok OK, $fail falharam"
echo ""

echo "=== TESTES SINTAXE/ERRADO (esperado: rejected) ==="
ok=0
fail=0
for f in sintaxe/errado/*.lan; do
  [ -f "$f" ] || continue
  out=$(java -cp .:tools/java-cup-11b-runtime.jar Lang2Compiler -syn "$f" 2>&1)
  result=$(echo "$out" | tail -1)
  if [ "$result" = "rejected" ]; then
    echo "OK: $f (corretamente rejeitado)"
    ok=$((ok+1))
  else
    echo "FALHOU: $f (esperado rejected, obteve: $result)"
    fail=$((fail+1))
  fi
done
echo "Errado: $ok OK, $fail falharam"
