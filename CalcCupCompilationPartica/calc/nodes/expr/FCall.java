package calc.nodes.expr;


import java.util.ArrayList;
import calc.nodes.CalcVisitor;

public class FCall extends Exp{

    private String id;
    private ArrayList<Exp> args;

    public FCall(int l, int c, String id, ArrayList<Exp> args){
         super(l,c);
         this.id = id;
         this.args = args;
    }

    public String getID(){return id;}
    public ArrayList<Exp> getArgs(){return args;}

    public void accept(CalcVisitor v){v.visit(this);}
}































