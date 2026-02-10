package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class Gt extends BinOp {
    public Gt(int line, int col, Exp arg1, Exp arg2) { 
        super(line, col, arg1, arg2); 
    }
    
    @Override
    public String toString() { return left + " > " + right; }
    
    @Override
    public void accept(CalcVisitor v) { v.visit(this); }
}