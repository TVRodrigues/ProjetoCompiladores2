package calc.nodes.types;
import calc.nodes.CalcVisitor;

public class TyChar extends CType {
    public TyChar(int line, int col) { super(line, col); }
    public String toString() { return "Char"; }
    public void accept(CalcVisitor v) { v.visit(this); }
}