// package lang.ast.decl;

// import java.util.ArrayList;
// import lang.ast.Node;
// import lang.ast.types.LType;
// import lang.ast.environment.Env;
// import lang.ast.NodeVisitor;


// public class FunDef extends Node {
//     private String fname;
//     private ArrayList<Bind> params;
//     private LType ret;
//     private Node body;

//     public FunDef(int l, int c, String s, ArrayList<Bind> params, LType ret, Node body) {
//         super(l, c);
//         fname = s;
//         this.params = params;
//         this.ret = ret;
//         this.body = body;
//     }

//     public String getFname() {
//         return fname;
//     }

//     public ArrayList<Bind> getParams() {
//         return params;
//     }

//     public Node getBody() {
//         return body;
//     }

//     public LType getRet() {
//         return ret;
//     }

//     public void accept(NodeVisitor v) {
//         v.visit(this);
//     }


// }
