package boolCalc.nodes;



import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;




public abstract class CNode {
     private int l,c;

      public CNode(int line, int col){
           l = line;
           c = col;
      }

      abstract public void interp(Env e);
      abstract public int toDot(DotFile d);

      public int getLine(){return l;}
      public int getCol(){return c;}

}
