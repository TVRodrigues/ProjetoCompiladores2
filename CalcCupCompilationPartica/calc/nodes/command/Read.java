package calc.nodes.command;

import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Read extends CNode {
    public Exp lvalue;

    // CORREÇÃO: Adicionado line e col
    public Read(int line, int col, Exp lvalue) {
        super(line, col);
        this.lvalue = lvalue;
    }

    @Override
    public String toString() {
        return "read " + lvalue;
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}