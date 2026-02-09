package calc.nodes.types;
import calc.nodes.CalcVisitor;

public class TyId extends CType {
    public String name;
    public TyId(int line, int col, String name) { 
        super(line, col); 
        this.name = name; 
    }
    public String toString() { return name; }
    public void accept(CalcVisitor v) { v.visit(this); }
}