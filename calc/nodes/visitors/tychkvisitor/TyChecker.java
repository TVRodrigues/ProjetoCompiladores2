package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import java.util.Hashtable;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayList;

public  class TyChecker extends CalcVisitor{

    private LinkedList<String> errors;
    private Stack<VType> stk;
    private Hashtable<String,TypeEntry> ctx;
    private Hashtable<String, VType> localCtx;
    private Hashtable<Object, VType> typeNode;

    public TyChecker(){
        errors = new LinkedList<String>();
        stk = new Stack<VType>();
        ctx = new Hashtable<String,TypeEntry>();
        typeNode = new Hashtable<Object,VType>();
    }

    public Hashtable<String,TypeEntry> getTypeCtx(){
        return ctx;
    }

    public Hashtable<Object, VType> getTypeNodes(){
        return typeNode;
    }

    public TypeEntry getFynctionType(String s){
       return ctx.get(s);
    }

    public VType typeOf(Object node){
       return typeNode.get(node);
    }

    public void visit(Program p){
         collectType(p.getFuncs());
         for(FunDef f : p.getFuncs()){
             localCtx = ctx.get(f.getFname()).localCtx;
             f.accept(this);
         }
    }

    private void typeError(String s){
       throw new RuntimeException(s);
    }

    private void collectType(ArrayList<FunDef> lf){
        for(FunDef f : lf){

              TypeEntry e = new TypeEntry();
              e.sym = f.getFname();
              e.localCtx = new Hashtable<String,VType>();

              int typeln = f.getParams().size() + 1;
              for(Bind b : f.getParams()){
                 b.getType().accept(this);
                 e.localCtx.put( b.getVar().getName(), stk.peek());
              }
              f.getRet().accept(this);
              VType[] v = new VType[typeln];
              for(int i = typeln - 1; i >=0; i--){
                  v[i] = stk.pop();
              }

              e.ty =  new VTyFunc(v);

              ctx.put(f.getFname(),e);
              typeNode.put(f,e.ty);
         }
    }

    public void visit(FunDef d){

        d.getRet().accept(this);

        for(Bind b: d.getParams()){
           b.accept(this);

        }
        d.getBody().accept(this);

    }

    public void visit(Bind  d){

        d.getType().accept(this);

        d.getVar().accept(this);
    }

    public void visit(CSeq d){

          d.getLeft().accept(this);

          d.getRight().accept(this);

    }

    public void visit(CAttr d){
          d.getExp().accept(this);
          if(localCtx.get(d.getVar().getName()) == null){
              localCtx.put(d.getVar().getName(),stk.pop());
          }else{
            VType ty = localCtx.get(d.getVar().getName());
            if(!ty.match(stk.pop())){
              typeError(
               "Erro de tipo (" + d.getLine() + ", " + d.getCol() + ") tipo da var " +d.getVar().getName() + " incompativel"
               );
            }
          }
    }

    public void visit(Loop d){

          d.getCond().accept(this);
          VType tyc = stk.pop();
          if(! (tyc.getTypeValue() == CLTypes.BOOL)){
             typeError(
               "Erro de tipo (" + d.getLine() + ", " +
                                  d.getCol() +
                                  ") condição do laço deve ser bool"
               );
          }
          d.getBody().accept(this);
    }

    public void visit(If d){
          d.getCond().accept(this);
          VType tyc = stk.pop();

          if(!(tyc.getTypeValue() == CLTypes.BOOL)){
             typeError(
               "Erro de tipo (" + d.getLine() + ", " +
                                  d.getCol() +
                                  ") condição do teste deve ser bool"
               );
          }
          Hashtable<String,VType> lcal1 = (Hashtable<String,VType>)localCtx.clone();

          d.getThn().accept(this);


          if(d.getEls() != null){
             Hashtable<String,VType> lcal2 = (Hashtable<String,VType>)localCtx.clone();
             localCtx  = lcal1;
             d.getEls().accept(this);
             LinkedList<String> keys = new LinkedList<String>();
             for(java.util.Map.Entry<String,VType> ent : localCtx.entrySet()){
                if(! lcal2.containsKey(ent.getKey())){
                      keys.add(ent.getKey());
                      //System.out.println("To remove " + ent.getKey());
                }
             }
             for(String k : keys){localCtx.remove(k);}
          }else{
             localCtx = lcal1;
          }
    }

    public void visit(Return d){
         d.getExp().accept(this);
         typeNode.put(d,stk.peek());

    }
    public void visit(Print d){
         d.getExp().accept(this);
         VType td = stk.pop();
         if(td.getTypeValue() == CLTypes.INT ||
            td.getTypeValue() == CLTypes.FLOAT ||
            td.getTypeValue() == CLTypes.BOOL){
         }else{
            typeError("Erro de tipo (" + d.getLine() + ", " + d.getCol() + ") Operandos incompatíveis");
         }

    }

    public void visit(BinOp e){ }

    public void visit(Sub  e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Plus e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Times e){
            e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Div e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyInt.newInt());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyFloat.newFloat());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }

    }

    public void visit(Lte e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }


    public void visit(Lt e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }

    public void visit(Eq e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         VType td = stk.pop();
         VType te = stk.pop();
         if(td.getTypeValue() == CLTypes.INT &&
            te.getTypeValue() == CLTypes.INT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.FLOAT &&
                  te.getTypeValue() == CLTypes.FLOAT){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else if(td.getTypeValue() == CLTypes.BOOL &&
                  te.getTypeValue() == CLTypes.BOOL){
            stk.push(VTyBool.newBool());
            typeNode.put(e,stk.peek());
         }else{
            typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") Operandos incompatíveis");
         }
    }


    public void visit(Var e){
        VType ty = localCtx.get(e.getName());
        if(ty == null){
          typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") variavel não declarada: " + e.getName());
        }else{
          stk.push(ty);
          typeNode.put(e,stk.peek());
        }
    }

    public void visit(FCall e){

        for(Exp ex : e.getArgs()){
             ex.accept(this);
        }
        VType vt[] = new VType[e.getArgs().size()];
        for(int j = e.getArgs().size() -1; j >=0;j--){
            vt[j] = stk.pop();
        }
        TypeEntry tyd = ctx.get(e.getID() );
        if(tyd != null ){
           if(!((VTyFunc)tyd.ty).matchArgs(vt)){
              typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") chamada de função incompatível ");
           }
           stk.push(((VTyFunc)tyd.ty).getReturnType() );
           typeNode.put(e,stk.peek());
        }else{
          typeError("Erro de tipo (" + e.getLine() + ", " + e.getCol() + ") chamada a função não declarada " + e.getID());
        }
    }

    public void visit(IntLit e){   stk.push(VTyInt.newInt() ); typeNode.put(e,stk.peek());}
    public void visit(BoolLit e){  stk.push(VTyBool.newBool() ); typeNode.put(e,stk.peek());}
    public void visit(FloatLit e){ stk.push(VTyFloat.newFloat() ); typeNode.put(e,stk.peek());}

    public void visit(TyBool t){  stk.push(VTyBool.newBool() ); typeNode.put(t,stk.peek());}
    public void visit(TyInt t){   stk.push(VTyInt.newInt() ); typeNode.put(t,stk.peek());}
    public void visit(TyFloat t){ stk.push(VTyFloat.newFloat()); typeNode.put(t,stk.peek());}

    public static void printEnv(Hashtable<String,VType> t){
         for(java.util.Map.Entry<String,VType> ent : t.entrySet()){
            System.out.println(ent.getKey()+ " -> "+ent.getValue().toString());
         }
    }

}
