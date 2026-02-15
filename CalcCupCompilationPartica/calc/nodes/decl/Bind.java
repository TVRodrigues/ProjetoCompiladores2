package calc.nodes.decl;

import calc.nodes.CNode;
import calc.nodes.expr.Var;
import calc.nodes.types.CType;
import calc.nodes.CalcVisitor;

public class Bind extends CNode {

      private Var v;
      private CType t;

      public Bind(int line, int col, CType t, Var v){
          super(line,col);
          this.t = t;
          this.v = v;
      }

      public CType getType(){ return t;}
      public Var getVar(){ return v;}

      public void accept(CalcVisitor v){v.visit(this);}
}
