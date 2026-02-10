package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class IntLit extends Exp {
    public int value; // <--- PUBLIC

    public IntLit(int line, int col, int value) {
        super(line, col);
        this.value = value;
    }
    public String toString() { return ""+value; }
    public void accept(CalcVisitor v) { v.visit(this); }
}