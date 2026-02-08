package calc.nodes.visitors;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;


public  class SimpleVisitor extends CalcVisitor{

    public void visit(Program p){
         System.out.println("numero de funcoes : " + p.getFuncs().size() );
    }

    public void visit(FunDef d){ }
    public void visit(Bind  d){ }

    public void visit(CSeq d){ }
    public void visit(CAttr d){ }
    public void visit(Loop d){ }
    public void visit(If d){ }
    public void visit(Return d){ }
    public void visit(Print d){ }

    public void visit(BinOp e){ }
    public void visit(Sub  e){ }
    public void visit(Plus e){ }
    public void visit(Times e){ }
    public void visit(Div e){ }
    public void visit(Var e){ }

    public void visit(Lte e){ }
    public void visit(Lt e){ }
    public void visit(Eq e){ }

    public void visit(FCall e){ }
    public void visit(IntLit e){ }
    public void visit(BoolLit e){ }
    public void visit(FloatLit e){ }

    public void visit(TyBool t){ }
    public void visit(TyInt t){ }
    public void visit(TyFloat t){ }

}
