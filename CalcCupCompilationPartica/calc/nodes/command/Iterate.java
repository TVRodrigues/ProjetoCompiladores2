package calc.nodes.command;

import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Iterate extends CNode {
    public String varName; 
    public Exp condition;
    public CNode body;

    // CORREÇÃO: Adicionado line e col
    public Iterate(int line, int col, String varName, Exp condition, CNode body) {
        super(line, col);
        this.varName = varName;
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "iterate (" + (varName!=null ? varName + ":" : "") + condition + ") ...";
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}