package calc.nodes.expr;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

public class BoolLit extends Exp{

      private boolean value;
      public BoolLit(int line, int col, boolean value){
           super(line,col);
           this.value = value;
      }

      public boolean getValue(){ return value;}


      public void accept(CalcVisitor v){v.visit(this);}


}
