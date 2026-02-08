
package lang.ast.command;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class ReturnExpr extends Cmd {
    private Exp expr;

    public ReturnExpr(Exp expr) {
        super(0, 0); // Ajuste os valores conforme necessário
        this.expr = expr;
    }

    public Exp getExpr() {
        return expr;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}

