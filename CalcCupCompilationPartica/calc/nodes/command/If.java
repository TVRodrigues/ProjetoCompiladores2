package calc.nodes.command;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class If extends CNode {
    public Exp cond;
    public CNode thenMsg;
    public CNode elseMsg;

    public If(int line, int col, Exp cond, CNode thenMsg, CNode elseMsg) {
        super(line, col);
        this.cond = cond;
        this.thenMsg = thenMsg;
        this.elseMsg = elseMsg;
    }
    public String toString(){ return "if " + cond; }
    public void accept(CalcVisitor v) { v.visit(this); }
}