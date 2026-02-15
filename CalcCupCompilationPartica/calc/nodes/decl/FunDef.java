package calc.nodes.decl;
import calc.nodes.CNode;
import calc.nodes.CalcVisitor;
import calc.nodes.types.CType;
import java.util.List;

public class FunDef extends CNode {
    public String name;
    public CType retType;
    public List<Bind> params;
    public CNode body;

    public FunDef(int line, int col, String name, List<Bind> params, CType retType, CNode body) {
        super(line, col);
        this.name = name;
        this.retType = retType;
        this.params = params;
        this.body = body;
    }
    public String toString(){ return "fun " + name; }
    public void accept(CalcVisitor v) { v.visit(this); }
}