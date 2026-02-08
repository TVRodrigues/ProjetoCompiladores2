package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.Var;


public class Attrib extends Cmd {
    private Var var;
    private Exp expr;

    public Attrib(int left, int right, Var var, Exp expr) {
        super(left, right);
        this.var = var;
        this.expr = expr;
    }

    public Var getVar() {
        return var;
    }

    public Exp getExpr() {
        return expr;
    }


    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
