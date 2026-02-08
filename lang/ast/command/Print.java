// package lang.ast.command;

// import lang.ast.Node;
// import lang.ast.expr.Exp;
// import lang.ast.NodeVisitor;
// import lang.ast.environment.Env;

// public class Print extends Node {

//     private Exp e;

//     public Print(int line, int col, Exp e) {
//         super(line, col);
//         this.e = e;
//     }

//     public Exp getExp() {
//         return e;
//     }

//     public void accept(NodeVisitor v) {
//         v.visit(this);
//     }


// }


package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;

public class Print extends Cmd {
    private Exp expr;

    public Print(Exp expr) {
        super(0,0);
        this.expr = expr;
    }

    public Exp getExpr() {
        return expr;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}

