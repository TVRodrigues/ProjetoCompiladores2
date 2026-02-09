package calc.nodes;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.parser.Lang2ParserSym;


public abstract class CNode{
     private int l,c;

      public CNode(int line, int col){
           l = line;
           c = col;
      }

      public int getLine(){return l;}
      public int getCol(){return c;}

      public abstract void accept(CalcVisitor v);

}
