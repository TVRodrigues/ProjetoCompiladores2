package lang.ast.expr;
import lang.ast.NodeVisitor;

public class Minus extends BinOp {

    public Minus(int line, int col, Exp el, Exp er) {
          super(line, col, el, er);
    }

    public void accept(NodeVisitor v) {
          v.visit(this);
    }

}