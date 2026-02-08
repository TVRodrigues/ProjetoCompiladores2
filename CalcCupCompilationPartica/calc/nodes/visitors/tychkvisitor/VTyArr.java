package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;

public class VTyArr extends VType {

     private VType arg;


     public VTyArr(VType a){
        super(CLTypes.ARR);
        arg = a;
     }

     public VType getTyArg(){ return arg;}

     public boolean match(VType t){
          if (getTypeValue() == t.getTypeValue()){
              return arg.match( ((VTyArr)t).getTyArg());
          }
          return false;
     }
}
