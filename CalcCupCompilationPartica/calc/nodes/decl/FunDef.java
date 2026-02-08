package calc.nodes.decl;

import calc.nodes.CNode;
import calc.nodes.types.CType;
import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import java.util.ArrayList;
import calc.nodes.CalcVisitor;

public class FunDef extends CNode{

   private String fname;
   private ArrayList<Bind> params;
   private CType ret;
   private CNode body;

   public FunDef(int l, int c, String s, ArrayList<Bind> params, CType ret, CNode body){
       super(l,c);
       fname = s;
       this.params = params;
       this.ret = ret;
       this.body = body;
   }

   public String getFname(){return fname;}
   public ArrayList<Bind>  getParams(){return params;}
   public CNode getBody(){return body;}
   public CType getRet(){return ret;}

   public void accept(CalcVisitor v){v.visit(this);}

}
