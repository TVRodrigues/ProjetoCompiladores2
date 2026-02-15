package calc.nodes.types;


import calc.nodes.CalcVisitor;


public class TyBool extends CType {

      public TyBool(int line, int col){
          super(line,col);
      }

     public void accept(CalcVisitor v){v.visit(this);}

}
