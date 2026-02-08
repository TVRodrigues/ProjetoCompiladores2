package calc.nodes.expr;


import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import calc.nodes.CalcVisitor;

import calc.nodes.CNode;

public abstract class Exp extends CNode {

      public Exp(int l, int c){
          super(l,c);
      }

}
