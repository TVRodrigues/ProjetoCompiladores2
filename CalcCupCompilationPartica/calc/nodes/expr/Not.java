package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class Not extends Exp {
    public Exp arg;
    public Not(int line, int col, Exp arg) { 
        super(line, col); 
        this.arg = arg; 
    }
    public String toString() { return "!" + arg; }
    public void accept(CalcVisitor v) { v.visit(this); }
}