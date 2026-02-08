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

/*
 * public class Attrib extends Node {
 * 
 * private Exp lhs; private Exp e; public Attrib(int line, int col, Exp lhs, Exp e){
 * super(line,col); this.lhs = lhs; this.e = e; }
 * 
 * public Exp getExp(){return e;} public Exp getLhs(){return lhs;}
 * 
 * public void accept(NodeVisitor v){v.visit(this);}
 * 
 * 
 * }
 */

// public class Attrib extends Node {

// private Var v;
// private Exp e;

// public Attrib(int line, int col, Var v, Exp e) {
// super(line, col);
// this.v = v;
// this.e = e;
// }

// public Exp getExp() {
// return e;
// }

// public Var getVar() {
// return v;
// }

// public void accept(NodeVisitor v) {
// v.visit(this);
// }


// }

