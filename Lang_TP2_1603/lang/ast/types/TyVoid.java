package lang.ast.types;

import lang.ast.NodeVisitor;


public class TyVoid extends LType {

    public TyVoid(int line, int col) {
        super(line, col);
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }

}

