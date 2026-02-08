package calc.nodes.expr;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

public class IntLit extends Exp{

      private int value;
      public IntLit(int line, int col, int value){
           super(line,col);
           this.value = value;
      }

      public int getValue(){ return value;}


      public int toDot(DotFile d){
         return d.addNode(""+value);
      }

      public void accept(CalcVisitor v){v.visit(this);}


}
