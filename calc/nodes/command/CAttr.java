package calc.nodes.command;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.expr.Var;
import calc.nodes.CalcVisitor;

public class CAttr extends CNode {

      private Var v;
      private Exp e;

      public CAttr(int line, int col, Var v, Exp e){
          super(line,col);
          this.v = v;
          this.e = e;
      }

      public Exp getExp(){ return e;}
      public Var getVar(){ return v;}

     public void accept(CalcVisitor v){v.visit(this);}


}
