package calc.nodes.visitors;

import calc.nodes.*;
import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.environment.Env;
import java.util.Stack;
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
    public void visit(DataDecl d) {}

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

    public void visit(CAttr a) {
        if (a.lvalue instanceof Var) {
            String name = ((Var) a.lvalue).name;
            a.exp.accept(this);
            envStack.peek().put(name, lastValue);
        }
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

    public void visit(FCall e) {}
    public void visit(New e) {}
    public void visit(TyInt t) {}
    public void visit(TyFloat t) {}
    public void visit(TyBool t) {}
    public void visit(TyVoid t) {}
    public void visit(TyChar t) {}
    public void visit(TyArr t) {}
    public void visit(TyId t) {}
}