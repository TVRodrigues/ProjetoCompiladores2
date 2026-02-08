package lang.ast.command;

import lang.ast.Node;
import lang.ast.NodeVisitor;

public abstract class Cmd extends Node {

    public Cmd(int left, int right) {
        super(left, right);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);

    }
}
