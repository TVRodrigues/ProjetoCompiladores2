package calc.nodes.visitors;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;
import calc.nodes.visitors.tychkvisitor.TyChecker;
import calc.nodes.visitors.tychkvisitor.*;



//import java.io.BufferedWriter;
import java.util.*;
import org.stringtemplate.v4.*;
import java.io.*;

public  class CTranslateVisitor extends CalcVisitor{

    private STGroup cst;
    private ST res;
    private String filename;
    private TyChecker tychk;

    public CTranslateVisitor(String destinationFile, TyChecker tychk){
          cst = new STGroupFile("templates/CTemplate.stg") ;
          filename = destinationFile;
          this.tychk = tychk;
    }

    public void writeCFile() throws Exception{
        FileWriter f = new FileWriter(filename);
        String txt = res.render();
        f.write(txt,0,txt.length());
        f.close();
    }

    public void visit(Program p){
         ST s = cst.getInstanceOf("program");
         for(FunDef f : p.getFuncs()){
             f.accept(this);
             s.add("flist",res);
             s.add("flist","   ");
         }
         res = s;
    }

    private String ctypeName(VType t){
         switch(t.getTypeValue()){
              case CLTypes.INT: return "int";
              case CLTypes.FLOAT: return "float";
              case CLTypes.BOOL: return "int";
              case CLTypes.ARR: return "int";
              case CLTypes.FUNC: return "void*";
              case CLTypes.ERR: throw new RuntimeException("Não é possivel gerar código C para tipo erro.");
         }
         return "void";
    }

    public void visit(FunDef d){
         ST s = cst.getInstanceOf("func");
         Hashtable<String,TypeEntry> ctx = tychk.getTypeCtx();
         d.getRet().accept(this);
         s.add("ret",res);
         s.add("id",d.getFname());
         Hashtable<String, Integer> isparam = new Hashtable<String,Integer>();
         for(Bind b : d.getParams() ){
              b.accept(this);
              s.add("param",res);
              isparam.put(b.getVar().getName(),0);
         }
         Hashtable<String, VType> lcf = ctx.get(d.getFname()).localCtx;
         for(java.util.Map.Entry<String,VType> ent : lcf.entrySet()){
              if(!isparam.containsKey(ent.getKey()) ){
                  ST lc = cst.getInstanceOf("decl");
                  lc.add("v",ent.getKey());
                  lc.add("t",ctypeName( ent.getValue() ));
                  s.add("decl", lc);
              }
         }
         d.getBody().accept(this);
         s.add("body",res);
         res = s;
    }

    public void visit(Bind  d){
       ST s = cst.getInstanceOf("bind");
       d.getType().accept(this);
       s.add("type",res);
       s.add("var",d.getVar().getName());
       res = s;
    }

    public void visit(CSeq d){
       ST s =  cst.getInstanceOf("seq");
       d.getLeft().accept(this);
       s.add("lft",res);
       d.getRight().accept(this);
       s.add("rht",res);
       res = s;
    }

    public void visit(CAttr d){
       ST s =  cst.getInstanceOf("attr");
       d.getVar().accept(this);
       s.add("lval",res);
       d.getExp().accept(this);
       s.add("exp",res);
       res = s;
    }

    public void visit(Loop d){
        ST s =  cst.getInstanceOf("loop");
        d.getCond().accept(this);
        s.add("e",res);
        d.getBody().accept(this);
        s.add("body",res);
        res = s;
    }

    public void visit(If d){
        ST s =  cst.getInstanceOf("if");
        d.getCond().accept(this);
        s.add("e",res);
        d.getThn().accept(this);
        s.add("thn",res);
        if(d.getEls() != null){
            d.getEls().accept(this);
            s.add("els",res);
        }
        res = s;
    }

    public void visit(Return d){
       ST s = cst.getInstanceOf("returnCMD");
       d.getExp().accept(this);
       s.add("e",res);
       res = s;
    }

    public void visit(Print d){
       ST s = null;
       VType ty = tychk.typeOf(d.getExp());
       d.getExp().accept(this);
       if(ty == null){
           throw new RuntimeException("Tentativa de imprimir expressão mal-tipada");
       }else if(ty.getTypeValue() == CLTypes.INT || ty.getTypeValue() == CLTypes.BOOL){
           s = cst.getInstanceOf("printIBCMD");
           s.add("e",res);
       }else  if(ty.getTypeValue() == CLTypes.FLOAT){
           s = cst.getInstanceOf("printFloatCMD");
           s.add("e",res);
       }
       res = s;
    }

    public void visit(BinOp e){}

    public void visit(Sub  e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","-");
       res = s;
    }

    public void visit(Lte e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","<=");
       res = s;
    }

    public void visit(Lt e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","<");
       res = s;
    }

    public void visit(Eq e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","==");
       res = s;
    }

    public void visit(Plus e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","+");
       res = s;
    }

    public void visit(Times e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","*");
       res = s;
    }

    public void visit(Div e){
       ST s = cst.getInstanceOf("binop");
       e.getLeft().accept(this);
       ST aux1 = res;
       e.getRight().accept(this);
       s.add("expe",aux1);
       s.add("expd",res);
       s.add("op","/");
       res = s;
    }

    public void visit(Var e){
       ST s = cst.getInstanceOf("singlet");
       s.add("valor",e.getName());
       res = s;
    }

    public void visit(FCall e){
       ST s = cst.getInstanceOf("fcall");
       s.add("fname",e.getID());
       for(Exp arg : e.getArgs()){
           arg.accept(this);
           s.add("args",res);
       }
       res = s;
    }

    public void visit(IntLit e){
       ST s = cst.getInstanceOf("singlet");
       s.add("valor",e.getValue() +"");
       res = s;
    }
    public void visit(BoolLit e){
       ST s = cst.getInstanceOf("singlet");
       s.add("valor",e.getValue() +"");
       res = s;
    }
    public void visit(FloatLit e){
       ST s = cst.getInstanceOf("singlet");
       s.add("valor",e.getValue() +"");
       res = s;
    }

    public void visit(TyBool t){
       ST s = cst.getInstanceOf("simpleType");
       s.add("ty","bool");
       res = s;
    }
    public void visit(TyInt t){
       ST s = cst.getInstanceOf("simpleType");
       s.add("ty","int");
       res = s;
    }
    public void visit(TyFloat t){
        ST s = cst.getInstanceOf("simpleType");
        s.add("ty","float");
        res = s;
    }

}
