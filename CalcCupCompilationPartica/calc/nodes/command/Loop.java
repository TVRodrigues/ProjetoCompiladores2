package calc.nodes.command;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.expr.Exp;
import calc.nodes.CalcVisitor;

public class Loop extends CNode{

   private Exp cond;
   private CNode body;

   public Loop(int l, int c, Exp e, CNode body){
       super(l,c);
       cond = e;
       this.body = body;
   }

   public Exp getCond(){return cond;}
   public CNode getBody(){return body;}

   public void accept(CalcVisitor v){v.visit(this);}



}
