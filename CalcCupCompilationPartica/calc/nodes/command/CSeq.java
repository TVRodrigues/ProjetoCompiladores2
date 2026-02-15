package calc.nodes.command;
import calc.nodes.*;
import java.util.List;

public class CSeq extends CNode {
    public List<CNode> cmds;

    public CSeq(int line, int col, List<CNode> cmds) {
        super(line, col);
        this.cmds = cmds;
    }
    
    public String toString(){ return "{ ... }"; }
    public void accept(CalcVisitor v) { v.visit(this); }
}