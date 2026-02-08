package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Comma extends Exp {
    private Exp left;
    private Exp right;

    public Comma(int left, int right, Exp e1, Exp e2) {
        super(left, right);
        this.left = e1;
        this.right = e2;
    }

    public Exp getLeft() {
        return left;
    }

    public Exp getRight() {
        return right;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
