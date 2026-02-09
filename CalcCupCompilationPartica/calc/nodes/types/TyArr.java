package calc.nodes.types;
import calc.nodes.CalcVisitor;

public class TyArr extends CType {
    public CType arg;
    public TyArr(int line, int col, CType arg) { 
        super(line, col); 
        this.arg = arg; 
    }
    public String toString() { return arg + "[]"; }
    public void accept(CalcVisitor v) { v.visit(this); }
}