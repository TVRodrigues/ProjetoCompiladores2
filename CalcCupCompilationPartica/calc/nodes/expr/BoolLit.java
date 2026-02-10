package calc.nodes.expr;

import calc.nodes.CalcVisitor;

public class BoolLit extends Exp {
    public boolean value; // <--- PUBLIC

    public BoolLit(int line, int col, boolean value) {
        super(line, col);
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}