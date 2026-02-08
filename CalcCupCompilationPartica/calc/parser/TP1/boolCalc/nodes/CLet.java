package boolCalc.nodes;


import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;




public class CLet extends CNode {

      private CBind[] binds;
      private CExp ex;

      public CLet(int line, int col, CBind[] binds, CExp ex){
          super(line,col);
          left = l;
          right = r;
          this.binds = binds;
          this.ex = ex;
      }

      public CNode getLeft(){ return left;}
      public CNode getRight(){ return right;}

      public void interp(Env e){
           for(CBind cb : binds){
               cb.interp(e);
           }
           Boolean b = ex.eval(e);
           System.out.println(b.toString());
      }

      public int toDot(DotFile d){
         int root = d.addNode("Let");
         int n;
         for(CBind b : binds){
             n = b.toDot(d);
             d.addEdge(root,n)
         }
         n = ex.toDot(d);
         d.addEdge(root, n);
         return root;
      }
}
