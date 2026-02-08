package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;

public class VTyFloat extends VType {

     private static VTyFloat instance = null;
     private VTyFloat(){
        super(CLTypes.FLOAT);
     }

     public static VTyFloat newFloat(){
         if(instance == null){
             instance = new VTyFloat();
         }
         return instance;
     }

     public boolean match(VType t){ return getTypeValue() == t.getTypeValue();}

     public String toString(){ return "Float";}
}
