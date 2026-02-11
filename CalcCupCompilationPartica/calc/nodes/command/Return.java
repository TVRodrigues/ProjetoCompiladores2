package calc.nodes.command;


import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Return extends CNode {

      private Exp e;

      public Return(int line, int col, Exp e){
          super(line,col);
          this.e = e;
      }

      public Exp getExp(){ return e;}

      public void accept(CalcVisitor v){v.visit(this);}


}
