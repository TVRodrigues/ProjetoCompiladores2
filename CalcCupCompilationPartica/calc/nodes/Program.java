package calc.nodes;

import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import java.util.ArrayList;
import calc.nodes.decl.FunDef;

public class Program extends CNode{

   private ArrayList<FunDef> funcs;


   public Program(int l, int c, ArrayList<FunDef> fs){
       super(l,c);
       this.funcs = fs;
   }

   public ArrayList<FunDef> getFuncs(){return funcs;}

   public void accept(CalcVisitor v){v.visit(this);}

}
