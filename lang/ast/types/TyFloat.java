 package lang.ast.types;

 import lang.ast.NodeVisitor;
 import lang.ast.types.*;

 public class TyFloat extends LType {

    public TyFloat(int line, int col) {
         super(line, col);
    }

    public void accept(NodeVisitor v) {
         v.visit(this);
     }
} 
