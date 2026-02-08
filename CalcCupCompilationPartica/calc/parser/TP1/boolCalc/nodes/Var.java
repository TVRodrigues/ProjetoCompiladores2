package boolCalc.nodes;

import boolCalc.nodes.dotutils.DotFile;
import boolCalc.nodes.environment.Env;



public class Var extends CExp{

      private String name;
      public Var(int line, int col, String name){
           super(line,col);
           this.name = name;
      }

      public String getName(){ return name;}

      public boolean eval(Env e){
          return e.read(name);
      }

      public int toDot(DotFile d){
         return d.addNode(name);
      }
}
