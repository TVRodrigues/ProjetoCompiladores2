package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class IterateCmd extends Cmd {
    private Exp condition;
    private Cmd body;

    public IterateCmd(Exp condition, Cmd body) {
        super(0, 0);
        this.condition = condition;
        this.body = body;
    }

    public Exp getCondition() {
        return condition;
    }

    public Cmd getBody() {
        return body;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
