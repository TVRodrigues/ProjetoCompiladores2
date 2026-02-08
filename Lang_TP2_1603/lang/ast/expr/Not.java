package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Not extends Exp {
    private Exp er;

    public Not(int left, int right, Exp er) {
        super(left, right);
        this.er = er;
    }

    public Exp getExpr() {
        return er;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }

}
