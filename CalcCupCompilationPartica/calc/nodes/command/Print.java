package calc.nodes.command;

import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Print extends CNode {
    public Exp exp; // <--- PUBLIC e nome 'exp'

    public Print(int line, int col, Exp exp) {
        super(line, col);
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print " + exp.toString();
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}