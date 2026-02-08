package boolCalc.nodes;



import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public class Or extends BinOp {

      public Or(int line, int col, CExp el, CExp er){
           super(line,col,el,er);

      }
      public boolean eval(Env e){
           return getLeft().eval(e) || getRigth().eval(e);
      }

      public int toDot(DotFile d){
         int ne = getLeft().toDot(d);
         int nd = getRigth().toDot(d);
         int root = d.addNode("Or");
         d.addEdge(root, ne);
         d.addEdge(root, nd);
         return root;
      }
}


