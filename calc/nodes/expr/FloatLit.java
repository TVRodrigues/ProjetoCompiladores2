package calc.nodes.expr;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

public class FloatLit extends Exp{

      private float value;
      public FloatLit(int line, int col, float value){
           super(line,col);
           this.value = value;
      }

      public float getValue(){ return value;}


      public void accept(CalcVisitor v){v.visit(this);}


}
