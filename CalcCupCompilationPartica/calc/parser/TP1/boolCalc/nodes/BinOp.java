package boolCalc.nodes;



import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public abstract class BinOp extends CExp {
      private CExp left, rigth;

      public BinOp(int line, int col, CExp el, CExp er){
           super(line,col);
           left = el;
           rigth = er;
      }

      public CExp getLeft(){return left;}
      public CExp getRigth(){return rigth;}
}

