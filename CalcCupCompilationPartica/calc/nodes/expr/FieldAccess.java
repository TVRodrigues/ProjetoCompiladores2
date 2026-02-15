package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class FieldAccess extends Exp {
    public Exp exp;
    public String field;

    public FieldAccess(int line, int col, Exp exp, String field) {
        super(line, col);
        this.exp = exp;
        this.field = field;
    }

    @Override
    public String toString() { return exp.toString() + "." + field; }

    @Override
    public void accept(CalcVisitor v) { v.visit(this); }
}