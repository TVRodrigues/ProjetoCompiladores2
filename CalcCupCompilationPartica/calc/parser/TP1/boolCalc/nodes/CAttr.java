package calc.nodes;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;



public class CAttr extends CNode {

      private Var v;
      private Exp e;

      public CAttr(int line, int col, Var v, Exp e){
          super(line,col);
          this.v = v;
          this.e = e;
      }

      public Exp getExp(){ return e;}
      public Var getVar(){ return v;}

      public void interp(Env env){
         Integer val = e.eval(env);
         env.store(v.getName(),val);
      }

      public int toDot(DotFile d){
         int nv = v.toDot(d);
         int ne = e.toDot(d);
         int root = d.addNode("Attr");
         d.addEdge(root, nv);
         d.addEdge(root, ne);
         return root;
      }
}
