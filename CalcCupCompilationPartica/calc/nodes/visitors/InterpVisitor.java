package calc.nodes.visitors;

import calc.nodes.*;
import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.environment.Env;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InterpVisitor extends CalcVisitor {

    private Stack<Env> envStack;
    private Scanner scan = new Scanner(System.in);
    private Object lastValue; 

    public InterpVisitor() {
        this.envStack = new Stack<>();
        this.envStack.push(new Env());
    }

    // --- Execução do Programa ---
    public void visit(Program p) {
        for (CNode decl : p.decls) {
            if (decl instanceof FunDef) {
                FunDef f = (FunDef) decl;
                envStack.peek().put(f.name, f); 
            }
        }
        
        Object mainNode = envStack.peek().get("main");
        if (mainNode instanceof FunDef) {
            FunDef mainFunc = (FunDef) mainNode;
            mainFunc.body.accept(this);
        } else {
            System.err.println("Erro: Função 'main' não encontrada.");
        }
    }

    public void visit(FunDef d) {}

    // --- Declaração de Variável (x : Int;) ---
    public void visit(Bind b) {
        String name = b.getVar().name;
        CType type = b.getType();
        
        // Valor padrão
        Object initVal = null;
        if (type instanceof TyInt) initVal = 0;
        else if (type instanceof TyFloat) initVal = 0.0f;
        else if (type instanceof TyBool) initVal = false;
        
        envStack.peek().put(name, initVal);
    }

    // --- Comandos ---
    public void visit(CSeq s) {
        for (CNode cmd : s.cmds) {
            cmd.accept(this);
        }
    }

    public void visit(Print p) {
        p.exp.accept(this);
        System.out.println(lastValue);
    }

    public void visit(If i) {
        i.cond.accept(this);
        Boolean cond = (Boolean) lastValue;
        if (cond) {
            i.thenMsg.accept(this);
        } else if (i.elseMsg != null) {
            i.elseMsg.accept(this);
        }
    }

    public void visit(Iterate i) {
        i.condition.accept(this);
        int count = 0;
        if (lastValue instanceof Integer) count = (Integer) lastValue;
        else if (lastValue instanceof Float) count = ((Float) lastValue).intValue();
        
        for (int k = 0; k < count; k++) {
            if (i.varName != null) {
                envStack.peek().put(i.varName, k);
            }
            i.body.accept(this);
        }
    }
    
    public void visit(Read r) {
        if (r.lvalue instanceof Var) {
            String name = ((Var) r.lvalue).name;
            // System.out.print("> ");
            if (scan.hasNextInt()) envStack.peek().put(name, scan.nextInt());
            else if (scan.hasNextFloat()) envStack.peek().put(name, scan.nextFloat());
            else if (scan.hasNextBoolean()) envStack.peek().put(name, scan.nextBoolean());
            else envStack.peek().put(name, scan.next());
        }
    }
    
    public void visit(Return r) { /* Simplificado */ }
    public void visit(Loop l) {}

    // --- Operações ---
    public void visit(IntLit i) { lastValue = i.value; }
    public void visit(FloatLit f) { lastValue = f.value; }
    public void visit(BoolLit b) { lastValue = b.value; }

    public void visit(Plus e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 + (Integer)v2;
        else lastValue = ((Number)v1).floatValue() + ((Number)v2).floatValue();
    }

    public void visit(Sub e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 - (Integer)v2;
        else lastValue = ((Number)v1).floatValue() - ((Number)v2).floatValue();
    }
    
    public void visit(Times e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 * (Integer)v2;
        else lastValue = ((Number)v1).floatValue() * ((Number)v2).floatValue();
    }
    
    public void visit(Div e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 / (Integer)v2;
        else lastValue = ((Number)v1).floatValue() / ((Number)v2).floatValue();
    }
    
    public void visit(Mod e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        lastValue = (Integer)v1 % (Integer)v2;
    }

    public void visit(Eq e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        lastValue = v1.equals(v2);
    }

    public void visit(Neq e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        lastValue = !v1.equals(v2);
    }

    public void visit(Lt e) {
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 < (Integer)v2;
        else lastValue = ((Number)v1).floatValue() < ((Number)v2).floatValue();
    }
    
    public void visit(Lte e) {
        e.left.accept(this);  Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        
        if (v1 instanceof Integer && v2 instanceof Integer) {
            lastValue = (Integer)v1 <= (Integer)v2;
        } else if (v1 instanceof Number && v2 instanceof Number) {
            lastValue = ((Number)v1).floatValue() <= ((Number)v2).floatValue();
        } else {
            lastValue = false;
        }
    }

    public void visit(Gt e) {
        e.left.accept(this);  Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        
        if (v1 instanceof Integer && v2 instanceof Integer) {
            lastValue = (Integer)v1 > (Integer)v2;
        } else if (v1 instanceof Number && v2 instanceof Number) {
            lastValue = ((Number)v1).floatValue() > ((Number)v2).floatValue();
        } else {
            lastValue = false;
        }
    }

    public void visit(And e) {
        e.left.accept(this);
        if (!(Boolean)lastValue) lastValue = false;
        else { e.right.accept(this); }
    }

    public void visit(Not e) {
        e.arg.accept(this);
        lastValue = !((Boolean)lastValue);
    }

    public void visit(Var v) {
        Object val = envStack.peek().get(v.name);
        if (val == null) System.err.println("Erro: Variavel " + v.name + " nao inicializada");
        lastValue = val;
    }

    
    public void visit(ArrayAccess e) {
        // 1. Avalia o vetor
        e.array.accept(this);
        Object[] arr = (Object[]) lastValue; // Assume que é array
        
        if (arr == null) {
            System.err.println("Erro Runtime: Acesso a vetor nulo ou não inicializado.");
            return;
        }

        // 2. Avalia o índice
        e.index.accept(this);
        int idx = 0;
        if (lastValue instanceof Integer) idx = (Integer) lastValue;
        else if (lastValue instanceof Float) idx = ((Float) lastValue).intValue();

        // 3. Retorna o valor
        if (idx >= 0 && idx < arr.length) {
            lastValue = arr[idx];
        } else {
            System.err.println("Erro Runtime: Indice " + idx + " fora dos limites (Tam: " + arr.length + ")");
            lastValue = null; // Ou valor padrão
        }
    }

    // --- Structs (Declaração) ---
    public void visit(DataDecl d) {
        // Guarda a definição da struct no ambiente para saber quais campos criar depois
        envStack.peek().put(d.name, d);
    }

    // --- Criação (New) - Atualizado para Arrays e Structs ---
    public void visit(New e) {
        // Caso 1: Array (já tinhas este)
        if (e.size != null) {
            e.size.accept(this);
            int size = 0;
            if (lastValue instanceof Integer) size = (Integer) lastValue;
            
            Object[] array = new Object[size];
            Object def = null;
            if (e.type instanceof TyInt) def = 0;
            else if (e.type instanceof TyFloat) def = 0.0f;
            else if (e.type instanceof TyBool) def = false;
            
            for(int i=0; i<size; i++) array[i] = def;
            lastValue = array;
        } 
        // Caso 2: Struct (NOVO)
        else if (e.type instanceof TyId) {
            String structName = ((TyId)e.type).name;
            Object declObj = envStack.peek().get(structName);
            
            if (declObj instanceof DataDecl) {
                DataDecl decl = (DataDecl) declObj;
                // Representamos a struct como um HashMap<NomeCampo, Valor>
                Map<String, Object> instance = new HashMap<>();
                
                // Inicializa cada campo com valor padrão
                for (Bind b : decl.binding) { // 'binding' deve ser public no DataDecl
                    String fieldName = b.getVar().name;
                    CType t = b.getType();
                    
                    Object def = null;
                    if (t instanceof TyInt) def = 0;
                    else if (t instanceof TyFloat) def = 0.0f;
                    else if (t instanceof TyBool) def = false;
                    
                    instance.put(fieldName, def);
                }
                lastValue = instance;
            } else {
                System.err.println("Erro: Tipo '" + structName + "' nao definido.");
                lastValue = null;
            }
        }
    }

    // --- Acesso a Campo (p.x) ---
    public void visit(FieldAccess e) {
        e.exp.accept(this);
        if (lastValue instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> instance = (Map<String, Object>) lastValue;
            if (instance.containsKey(e.field)) {
                lastValue = instance.get(e.field);
            } else {
                System.err.println("Erro: Campo '" + e.field + "' nao existe.");
            }
        } else {
            System.err.println("Erro: Tentativa de acesso a campo em algo que nao e struct.");
        }
    }

    // --- Atribuição (Atualizado para p.x = 10) ---
    public void visit(CAttr a) {
        if (a.lvalue instanceof Var) {
            String name = ((Var) a.lvalue).name;
            a.exp.accept(this);
            envStack.peek().put(name, lastValue);
        } 
        else if (a.lvalue instanceof ArrayAccess) {
            // ... (código do array igual ao anterior) ...
            ArrayAccess aa = (ArrayAccess) a.lvalue;
            aa.array.accept(this);
            Object[] arr = (Object[]) lastValue;
            if (arr != null) {
                Object[] targetArray = arr; 
                aa.index.accept(this);
                int idx = (lastValue instanceof Integer) ? (Integer)lastValue : 0;
                a.exp.accept(this);
                if (idx >= 0 && idx < targetArray.length) targetArray[idx] = lastValue;
            }
        }
        // Caso 3: Campo de Struct (NOVO)
        else if (a.lvalue instanceof FieldAccess) {
            FieldAccess fa = (FieldAccess) a.lvalue;
            fa.exp.accept(this); // Avalia quem é o objeto (ex: 'p')
            
            if (lastValue instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> instance = (Map<String, Object>) lastValue;
                
                // Avalia o valor a atribuir
                a.exp.accept(this);
                
                if (instance.containsKey(fa.field)) {
                    instance.put(fa.field, lastValue);
                } else {
                    System.err.println("Erro: Campo '" + fa.field + "' inexistente.");
                }
            }
        }
    }
    public void visit(FCall e) {}
    public void visit(TyInt t) {}
    public void visit(TyFloat t) {}
    public void visit(TyBool t) {}
    public void visit(TyVoid t) {}
    public void visit(TyChar t) {}
    public void visit(TyArr t) {}
    public void visit(TyId t) {}
}