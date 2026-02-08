package calc.nodes.command;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CNode;
import calc.nodes.CalcVisitor;


public class CSeq extends CNode {

      private CNode left;
      private CNode right;

      public CSeq(int line, int col, CNode l, CNode r){
          super(line,col);
          left = l;
          right = r;
      }

      public CNode getLeft(){ return left;}
      public CNode getRight(){ return right;}

      public void accept(CalcVisitor v){v.visit(this);}
}
