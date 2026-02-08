package boolCalc.nodes;



import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public abstract class CExp extends CNode {

      public Exp(int l, int c){
          super(l,c);
      }

      public void interp(Env e){
         return;
      }

      public abstract boolean eval(Env e);
}
