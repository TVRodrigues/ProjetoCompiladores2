package calc.nodes.expr;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

public class Div extends BinOp {

      public Div(int line, int col, Exp el, Exp er){
           super(line,col,el,er);

      }

      public void accept(CalcVisitor v){v.visit(this);}
}


