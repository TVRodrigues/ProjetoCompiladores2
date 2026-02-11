package calc.nodes.types;


import calc.nodes.environment.Env;

import calc.nodes.CalcVisitor;

public class TyFloat extends CType {

      public TyFloat(int line, int col){
          super(line,col);
      }

     public void accept(CalcVisitor v){v.visit(this);}

}
