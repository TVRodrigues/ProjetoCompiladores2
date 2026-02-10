package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class Var extends Exp {
    public String name; // <--- PUBLIC

    public Var(int line, int col, String name) {
        super(line, col);
        this.name = name;
    }
    public String toString() { return name; }
    public void accept(CalcVisitor v) { v.visit(this); }
}