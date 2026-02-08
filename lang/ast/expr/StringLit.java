package lang.ast.expr;

import lang.ast.NodeVisitor;

public class StringLit extends Exp {
    private String value;

    public StringLit(int left, int right, String value) {
        super(left, right);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
