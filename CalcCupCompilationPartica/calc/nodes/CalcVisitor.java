package calc.nodes;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;

public abstract class CalcVisitor {

    // --- Program ---
    public abstract void visit(Program p);

    // --- Declarations ---
    public abstract void visit(FunDef d);
    public abstract void visit(Bind d);
    public abstract void visit(DataDecl d);

    // --- Commands ---
    public abstract void visit(CSeq d);
    public abstract void visit(CAttr d);
    public abstract void visit(Loop d);
    public abstract void visit(Iterate d);
    public abstract void visit(If d);
    public abstract void visit(Return d);
    public abstract void visit(Print d);
    public abstract void visit(Read d);

    // --- Expressions ---
    // Removido visit(BinOp) para evitar confusão se não for estritamente necessario
    // Se precisar, descomente: public abstract void visit(BinOp e);
    
    public abstract void visit(Sub e);
    public abstract void visit(Plus e);
    public abstract void visit(Times e);
    public abstract void visit(Div e);
    public abstract void visit(Mod e);
    
    public abstract void visit(Lte e);
    public abstract void visit(Lt e);
    public abstract void visit(Gt e);
    public abstract void visit(Eq e);
    public abstract void visit(Neq e);
    
    public abstract void visit(And e);
    public abstract void visit(Not e);
    
    public abstract void visit(Var e);
    public abstract void visit(FCall e);
    public abstract void visit(New e);
    
    public abstract void visit(IntLit e);
    public abstract void visit(BoolLit e);
    public abstract void visit(FloatLit e);

    // --- Types ---
    public abstract void visit(TyBool t);
    public abstract void visit(TyInt t);
    public abstract void visit(TyFloat t);
    public abstract void visit(TyVoid t);
    public abstract void visit(TyChar t);
    public abstract void visit(TyArr t);
    public abstract void visit(TyId t);

    public abstract void visit(ArrayAccess e);
    public abstract void visit(FieldAccess e);
}