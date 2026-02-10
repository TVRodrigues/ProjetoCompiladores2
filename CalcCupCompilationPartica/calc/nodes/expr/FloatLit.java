package calc.nodes.expr;

import calc.nodes.CalcVisitor;

public class FloatLit extends Exp {
    public float value; // <--- PUBLIC

    public FloatLit(int line, int col, float value) {
        super(line, col);
        this.value = value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}