package calc.nodes.expr;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

public class Var extends Exp{

      private String name;
      public Var(int line, int col, String name){
           super(line,col);
           this.name = name;
      }

      public String getName(){ return name;}

      public void accept(CalcVisitor v){v.visit(this);}

}
