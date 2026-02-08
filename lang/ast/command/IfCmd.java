package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;


public class IfCmd extends Cmd {
    private Exp condition;
    private Cmd command;
    private Cmd elseCmd;

    public IfCmd(Exp condition, Cmd command, Cmd elseCmd) {
        super(0,0);
        this.condition = condition;
        this.command = command;
        this.elseCmd = elseCmd;
    }

    public Exp getCondition() {
        return condition;
    }

    public Cmd getCommand() {
        return command;
    }
    public Cmd getElseCmd() {
        return elseCmd;
    }
    public boolean hasElse() {
        return elseCmd != null;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
