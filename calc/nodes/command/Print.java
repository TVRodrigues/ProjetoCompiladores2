package calc.nodes.command;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Print extends CNode {

      private Exp e;

      public Print(int line, int col, Exp e){
          super(line,col);
          this.e = e;
      }

      public Exp getExp(){ return e;}

      public void accept(CalcVisitor v){v.visit(this);}


}
