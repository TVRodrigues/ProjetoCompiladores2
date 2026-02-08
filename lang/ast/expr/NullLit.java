package lang.ast.expr;

import lang.ast.NodeVisitor;

public class NullLit extends Exp {

    public NullLit(int left, int right) {
        super(left, right);
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
