// package lang.ast.command;

// import lang.ast.Node;
// import lang.ast.NodeVisitor;
// import lang.ast.environment.Env;




// public class Seq extends Node {

//     private Node left;
//     private Node right;

//     public Seq(int line, int col, Node l, Node r) {
//         super(line, col);
//         left = l;
//         right = r;
//     }

//     public Node getLeft() {
//         return left;
//     }

//     public Node getRight() {
//         return right;
//     }

//     public void accept(NodeVisitor v) {
//         v.visit(this);
//     }
// }
