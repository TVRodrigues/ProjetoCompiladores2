// package lang.ast.expr;

// import java.util.ArrayList;
// import lang.ast.environment.Env;
// import lang.ast.NodeVisitor;


// public class FCall extends Exp {

//     private String id;
//     private ArrayList<Exp> args;

//     public FCall(int l, int c, String id, ArrayList<Exp> args) {
//         super(l, c);
//         this.id = id;
//         this.args = args;
//     }

//     public String getID() {
//         return id;
//     }

//     public ArrayList<Exp> getArgs() {
//         return args;
//     }

//     public void accept(NodeVisitor v) {
//         v.visit(this);
//     }
// }

