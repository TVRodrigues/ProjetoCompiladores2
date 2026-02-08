package lang.ast.types;

import lang.ast.NodeVisitor;

public class UserType extends LType {

    private String typeName;

    public UserType(int line, int col, String typeName) {
        super(line, col);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
