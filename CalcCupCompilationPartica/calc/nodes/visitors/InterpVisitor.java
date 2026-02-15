package calc.nodes.visitors;

import calc.nodes.*;
import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.environment.Env;
import java.util.Stack;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class InterpVisitor extends CalcVisitor {

    private Stack<Env> envStack;
    private Scanner scan = new Scanner(System.in);
    private Object lastValue; 

    public InterpVisitor() {
        this.envStack = new Stack<>();
        this.envStack.push(new Env());
    }

    private boolean isTrue(Object o) {
        if (o instanceof Boolean) return (Boolean) o;
        if (o instanceof Integer) return (Integer) o != 0;
        if (o instanceof Float) return ((Float) o) != 0.0f;
        return false;
    }

    public void visit(Program p) {
        for (CNode decl : p.decls) {
            if (decl instanceof FunDef) {
                FunDef f = (FunDef) decl;
                envStack.peek().put(f.name, f); 
            } 
            else if (decl instanceof DataDecl) {
                DataDecl d = (DataDecl) decl;
                envStack.peek().put(d.name, d);
            }
        }
        
        Object mainNode = envStack.peek().get("main");
        if (mainNode instanceof FunDef) {
            FunDef mainFunc = (FunDef) mainNode;
            mainFunc.body.accept(this);
        } else {
            System.err.println("Erro: Função 'main' não encontrada.");
            System.err.println("Ambiente Global contém: " + envStack.peek());
        }
    }

    public void visit(FunDef d) {}
    public void visit(DataDecl d) {}

    public void visit(Bind b) {
        String name = b.getVar().name;
        CType type = b.getType();
        Object initVal = null;
        if (type instanceof TyInt) initVal = 0;
        else if (type instanceof TyFloat) initVal = 0.0f;
        else if (type instanceof TyBool) initVal = false;
        envStack.peek().put(name, initVal);
    }

    public void visit(CSeq s) {
        for (CNode cmd : s.cmds) cmd.accept(this);
    }

    public void visit(Print p) {
        p.exp.accept(this);
        System.out.println(lastValue);
    }

    public void visit(CAttr a) {
        if (a.lvalue instanceof Var) {
            String name = ((Var) a.lvalue).name;
            a.exp.accept(this);
            envStack.peek().put(name, lastValue);
        } 
        else if (a.lvalue instanceof ArrayAccess) {
            ArrayAccess aa = (ArrayAccess) a.lvalue;
            aa.array.accept(this);
            Object[] arr = (Object[]) lastValue;
            if (arr != null) {
                Object[] targetArray = arr; 
                aa.index.accept(this);
                int idx = (lastValue instanceof Integer) ? (Integer)lastValue : ((Float)lastValue).intValue();
                a.exp.accept(this);
                if (idx >= 0 && idx < targetArray.length) targetArray[idx] = lastValue;
                else System.err.println("Erro Runtime: Indice fora dos limites.");
            }
        }
        else if (a.lvalue instanceof FieldAccess) {
            FieldAccess fa = (FieldAccess) a.lvalue;
            fa.exp.accept(this);
            if (lastValue instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> instance = (Map<String, Object>) lastValue;
                a.exp.accept(this);
                if (instance.containsKey(fa.field)) instance.put(fa.field, lastValue);
                else System.err.println("Erro: Campo '" + fa.field + "' inexistente.");
            }
        }
    }

    public void visit(If i) {
        i.cond.accept(this);
        if (isTrue(lastValue)) {
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
            if (i.varName != null) envStack.peek().put(i.varName, k);
            i.body.accept(this);
        }
    }
    
    public void visit(Read r) {
        if (r.lvalue instanceof Var) {
            String name = ((Var) r.lvalue).name;
            if (scan.hasNextInt()) envStack.peek().put(name, scan.nextInt());
            else if (scan.hasNextFloat()) envStack.peek().put(name, scan.nextFloat());
            else if (scan.hasNextBoolean()) envStack.peek().put(name, scan.nextBoolean());
            else envStack.peek().put(name, scan.next());
        }
    }
    
    public void visit(Return r) {}

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
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 <= (Integer)v2;
        else lastValue = ((Number)v1).floatValue() <= ((Number)v2).floatValue();
    }
    
    public void visit(Gt e) { 
        e.left.accept(this); Object v1 = lastValue;
        e.right.accept(this); Object v2 = lastValue;
        if (v1 instanceof Integer && v2 instanceof Integer) lastValue = (Integer)v1 > (Integer)v2;
        else lastValue = ((Number)v1).floatValue() > ((Number)v2).floatValue();
    }

    public void visit(Or e) {
        e.left.accept(this);
        if (isTrue(lastValue)) {
            lastValue = true;
        } else {
            e.right.accept(this);
            lastValue = isTrue(lastValue);
        }
    }

    public void visit(And e) {
        e.left.accept(this);
        if (!isTrue(lastValue)) {
            lastValue = false;
        } else {
            e.right.accept(this);
            lastValue = isTrue(lastValue);
        }
    }

    public void visit(Not e) {
        e.arg.accept(this);
        lastValue = !isTrue(lastValue);
    }

    public void visit(Var v) {
        Object val = envStack.peek().get(v.name);
        if (val == null) System.err.println("Erro: Variavel " + v.name + " nao inicializada");
        lastValue = val;
    }
    
    public void visit(New e) {
        if (e.size != null) {
            e.size.accept(this);
            int size = (lastValue instanceof Integer) ? (Integer) lastValue : 0;
            Object[] array = new Object[size];
            Object def = (e.type instanceof TyInt) ? 0 : (e.type instanceof TyFloat ? 0.0f : false);
            for(int i=0; i<size; i++) array[i] = def;
            lastValue = array;
        } 
        else if (e.type instanceof TyId) {
            String structName = ((TyId)e.type).name;
            Object declObj = envStack.peek().get(structName);
            if (declObj instanceof DataDecl) {
                DataDecl decl = (DataDecl) declObj;
                Map<String, Object> instance = new HashMap<>();
                for (Bind b : decl.binding) {
                    Object def = (b.getType() instanceof TyInt) ? 0 : (b.getType() instanceof TyFloat ? 0.0f : false);
                    instance.put(b.getVar().name, def);
                }
                lastValue = instance;
            }
        }
    }
    
    public void visit(ArrayAccess e) {
        e.array.accept(this);
        Object[] arr = (Object[]) lastValue;
        if (arr != null) {
            e.index.accept(this);
            int idx = (lastValue instanceof Integer) ? (Integer)lastValue : 0;
            if (idx >= 0 && idx < arr.length) lastValue = arr[idx];
            else System.err.println("Erro: Indice invalido");
        }
    }

    public void visit(FieldAccess e) {
        e.exp.accept(this);
        if (lastValue instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> instance = (Map<String, Object>) lastValue;
            if (instance.containsKey(e.field)) lastValue = instance.get(e.field);
            else System.err.println("Erro: Campo '" + e.field + "' nao existe.");
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