package lang.ast.expr;
// import lang.ast.nodes.dotutils.DotFile;
import lang.ast.environment.Env;
import lang.ast.NodeVisitor;

public class Div extends BinOp{
    private Exp er;
    private Exp el;
    public Div(int line, int col, Exp el, Exp er){
        super(line,col,el,er);
        this.er = er;
        this.el = el;
   }

   public void accept(NodeVisitor v){v.visit(this);}
}

