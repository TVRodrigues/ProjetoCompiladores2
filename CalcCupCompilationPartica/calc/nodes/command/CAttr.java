package calc.nodes.command;
import calc.nodes.*;
import calc.nodes.expr.Exp;

public class CAttr extends CNode {
    public Exp lvalue; // Mudou de Var para Exp
    public Exp exp;

    public CAttr(int line, int col, Exp lvalue, Exp exp) {
        super(line, col);
        this.lvalue = lvalue;
        this.exp = exp;
    }
    
    public String toString(){ return lvalue + " = " + exp; }
    public void accept(CalcVisitor v) { v.visit(this); }
}