package calc.nodes.expr;


import calc.nodes.CalcVisitor;

public class Times extends BinOp {

      public Times(int line, int col, Exp el, Exp er){
           super(line,col,el,er);

      }

      public void accept(CalcVisitor v){v.visit(this);}


}


