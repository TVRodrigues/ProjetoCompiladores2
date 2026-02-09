package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class Neq extends BinOp {
    public Neq(int line, int col, Exp arg1, Exp arg2) { super(line, col, arg1, arg2); }
    public String toString() { return left + " != " + right; }
    public void accept(CalcVisitor v) { v.visit(this); }
}