package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public abstract class BinOp extends Exp {
    public Exp left;
    public Exp right;

    public BinOp(int line, int col, Exp left, Exp right) {
        super(line, col);
        this.left = left;
        this.right = right;
    }
}