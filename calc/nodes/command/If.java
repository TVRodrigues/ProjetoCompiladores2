package calc.nodes.command;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class If extends CNode{

   private Exp cond;
   private CNode thn;
   private CNode els;
   public If(int l, int c, Exp e, CNode thn, CNode els){
       super(l,c);
       cond = e;
       this.thn = thn;
       this.els = els;
   }

   public Exp getCond(){return cond;}
   public CNode getThn(){return thn;}
   public CNode getEls(){return els;}

   public void accept(CalcVisitor v){v.visit(this);}



}
