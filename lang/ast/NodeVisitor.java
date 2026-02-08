// package lang.ast;

// // import lang.ast.decl.*;
// import lang.ast.command.*;
// import lang.ast.decl.*;
// import lang.ast.expr.*;
// import lang.ast.types.*;


// public abstract class NodeVisitor {

// public abstract void visit(Program p);

// public abstract void visit(Attrib e);

// public abstract void visit(Seq e);

// public abstract void visit(If e);

// public abstract void visit(Loop e);

// public abstract void visit(Print e);

// public abstract void visit(Return e);

// public abstract void visit(Bind e);

// public abstract void visit(FunDef e);

// public abstract void visit(Sub e);

// public abstract void visit(Plus e);

// public abstract void visit(Times e);

// public abstract void visit(Div e);

// public abstract void visit(Var e);

// public abstract void visit(FCall e);

// public abstract void visit(IntLit e);

// public abstract void visit(BoolLit e);

// public abstract void visit(FloatLit e);

// public abstract void visit(TyBool t);

// public abstract void visit(TyInt t);

// public abstract void visit(TyFloat t);

// }

package lang.ast;

// import lang.ast.decl.*;
import lang.ast.command.*;
import lang.ast.expr.*;
import lang.ast.types.*;


public abstract class NodeVisitor {

    public abstract void visit(Attrib c);

    public abstract void visit(MainBlock c);

    public abstract void visit(ReturnExpr c);



    // public abstract void visit(Id e);

    public abstract void visit(Print c);

    public abstract void visit(Cmd c);

    public abstract void visit(IfCmd c);

    public abstract void visit(IterateCmd c);

    public abstract void visit(Sub e);

    public abstract void visit(Plus e);

    public abstract void visit(Minus e);

    public abstract void visit(Times e);

    public abstract void visit(Div e);

    public abstract void visit(Var e);

    public abstract void visit(IntLit e);

    public abstract void visit(CharLit e);

    public abstract void visit(BoolLit e);

    public abstract void visit(StringLit e);

    public abstract void visit(FloatLit e);

    public abstract void visit(Mod e);

    public abstract void visit(MenorQ e);

    public abstract void visit(MaiorQ e);

    public abstract void visit(Igual e);

    public abstract void visit(Diferente e);

    public abstract void visit(And e);

    public abstract void visit(Not e);

    public abstract void visit(Comma e);

    public abstract void visit(NullLit e);

    public abstract void visit(TyBool t);

    public abstract void visit(TyInt t);

    public abstract void visit(TyChar t);

    public abstract void visit(TyFloat t);

    public abstract void visit(TyVoid t);

    public abstract void visit(UserType t);

    public abstract void visit(Decl t);

    public abstract void visit(DataDecl t);

    public abstract void visit(Func t);

    public abstract void visit(Param t);


}
