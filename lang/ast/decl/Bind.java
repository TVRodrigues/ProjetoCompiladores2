// package lang.ast.decl;

// import lang.ast.Node;

// import lang.ast.expr.Var;
// import lang.ast.types.LType;
// import lang.ast.environment.Env;
// import lang.ast.NodeVisitor;

// public class Bind extends Node {

//     private Var v;
//     private LType t;

//     public Bind(int line, int col, LType t, Var v) {
//         super(line, col);
//         this.t = t;
//         this.v = v;
//     }

//     public lang.ast.types.LType getType() {
//         return t;
//     }

//     public Var getVar() {
//         return v;
//     }

//     public void accept(NodeVisitor v) {
//         v.visit(this);
//     }

// }
