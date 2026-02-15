package calc.nodes.expr;


import calc.nodes.CalcVisitor;

public class Sub extends BinOp {

      public Sub(int line, int col, Exp el, Exp er){
           super(line,col,el,er);
      }

      public void accept(CalcVisitor v){v.visit(this);}

}


