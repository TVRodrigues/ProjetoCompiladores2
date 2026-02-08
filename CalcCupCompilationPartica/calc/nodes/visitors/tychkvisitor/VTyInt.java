package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;

public class VTyInt extends VType {

     private static VTyInt instance = null;
     private VTyInt(){
        super(CLTypes.INT);
     }

     public static VTyInt newInt(){
         if(instance == null){
             instance = new VTyInt();
         }
         return instance;
     }

     public boolean match(VType t){ return getTypeValue() == t.getTypeValue();}

     public String toString(){ return "Int";}
}
