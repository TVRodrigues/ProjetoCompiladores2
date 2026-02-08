package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyChar extends LType {
    public TyChar(int var1, int var2) {
        super(var1, var2);
    }

    public void accept(NodeVisitor var1) {
        var1.visit(this);
    }
}
