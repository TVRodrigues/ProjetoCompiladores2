package boolCalc.nodes;


import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;


public class BoolLit extends CExp{

      private boolean value;
      public BoolLit(int line, int col, boolean value){
           super(line,col);
           this.value = value;
      }

      public boolean getValue(){ return value;}

      public boolean eval(Env e){
          return value;
      }

      public int toDot(DotFile d){
         return d.addNode(value ? "t":"f");
      }
}
