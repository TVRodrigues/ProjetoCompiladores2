package calc.nodes.types;
import calc.nodes.CalcVisitor;

public class TyVoid extends CType {
    public TyVoid(int line, int col) { super(line, col); }
    public String toString() { return "Void"; }
    public void accept(CalcVisitor v) { v.visit(this); }
}