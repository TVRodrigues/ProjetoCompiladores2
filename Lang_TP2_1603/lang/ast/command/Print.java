

package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class Print extends Cmd {
    private Exp expr;

    public Print(Exp expr) {
        super(0,0);
        this.expr = expr;
    }

    public Exp getExpr() {
        return expr;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}

