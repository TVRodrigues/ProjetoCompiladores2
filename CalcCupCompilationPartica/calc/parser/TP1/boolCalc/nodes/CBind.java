
package boolCalc.nodes;



import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public abstract class CBind extends CNode {
      private int l,c;
      private Var v;
      private CExp e;

      public CBind(int line, int col, Var v, CExp e){
           l = line;
           c = col;
           this.v = v;
           this.e = e;
      }
      public Exp getExp(){ return e;}
      public Var getVar(){ return v;}

      public void interp(Env env){
         Boolean val = e.eval(env);
         env.store(v.getName(),val);
      }

      public int toDot(DotFile d){
         int nv = v.toDot(d);
         int ne = e.toDot(d);
         int root = d.addNode("Bind");
         d.addEdge(root, nv);
         d.addEdge(root, ne);
         return root;
      }
}
