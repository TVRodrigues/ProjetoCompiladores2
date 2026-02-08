package boolCalc.nodes;


import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public class And extends BinOp {

      public And(int line, int col, Exp el, Exp er){
           super(line,col,el,er);
      }

      public boolean eval(Env e){
           return getLeft().eval(e) && getRigth().eval(e);
      }

      public int toDot(DotFile d){
         int ne = getLeft().toDot(d);
         int nd = getRigth().toDot(d);
         int root = d.addNode("And");
         d.addEdge(root, ne);
         d.addEdge(root, nd);
         return root;
      }
}


