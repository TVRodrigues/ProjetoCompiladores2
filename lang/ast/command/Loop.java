// package lang.ast.command;

// import lang.ast.Node;
// import lang.ast.NodeVisitor;
// import lang.ast.expr.Exp;
// import lang.ast.environment.Env;

// public class Loop extends Node {

//     private Exp cond;
//     private Node body;

//     public Loop(int l, int c, Exp e, Node body) {
//         super(l, c);
//         this.cond = e;
//         this.body = body;
//     }

//     public Exp getCond() {
//         return cond;
//     }

// public Node getBody(){return body;}

// public void accept(NodeVisitor v){v.visit(this);}

// }