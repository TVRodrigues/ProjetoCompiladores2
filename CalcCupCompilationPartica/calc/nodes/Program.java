package calc.nodes;
import java.util.List;

public class Program extends CNode {
    public List<CNode> decls;

    public Program(int line, int col, List<CNode> decls) {
        super(line, col);
        this.decls = decls;
    }
    
    public String toString(){ return "Program"; }
    
    public void accept(CalcVisitor v) { v.visit(this); }
}