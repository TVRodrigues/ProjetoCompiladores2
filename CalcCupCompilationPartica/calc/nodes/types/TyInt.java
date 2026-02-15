package calc.nodes.types;


import calc.nodes.CalcVisitor;


public class TyInt extends CType {

      public TyInt(int line, int col){
          super(line,col);
      }
      public void accept(CalcVisitor v){v.visit(this);}

}
