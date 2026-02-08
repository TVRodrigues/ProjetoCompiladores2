package calc.nodes.visitors;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;
import calc.nodes.environment.Env;
import java.util.Stack;
import java.util.Hashtable;

public  class InterpVisitor extends CalcVisitor{

    private Env env;

    private Stack<Object> stk;
    private Hashtable<String,FunDef> fn;
    private boolean retMode;

    public InterpVisitor(){
         stk = new Stack<Object>();
         fn = new Hashtable<String,FunDef>();
         retMode = false;
         env = new Env();
    }

    public void printEnv(){ env.dumpTable(); }

    public void visit(Program p){
         FunDef start = null;
         for(FunDef f : p.getFuncs()){
             fn.put(f.getFname(), f);
             if(f.getFname().equals("inicio")){
                  start = f;
             }
         }
         if(start != null){
             start.getBody().accept(this);
         }else{
             throw new RuntimeException("Erro: Não há uma função de início no programa.");
         }
    }

    public void visit(FunDef d){
        d.getBody().accept(this);
    }

    public void visit(Bind  d){
       // Nção precisamos fazer nada aqui,
       // O Fcall já toma as providências necessárias !
    }

    public void visit(CSeq d){
        if(!retMode){
           d.getLeft().accept(this);
           if(!retMode){
              d.getRight().accept(this);
           }
        }
    }

    public void visit(CAttr d){
         if(!retMode){
            d.getExp().accept(this);
            env.store(d.getVar().getName(), stk.pop());
         }
    }

    public void visit(Loop d){
          if(!retMode){
             d.getCond().accept(this);
             while((boolean)stk.pop()){
                 d.getBody().accept(this);
                 if(retMode){
                     return;
                 }
                 d.getCond().accept(this);
             }
          }
    }

    public void visit(If d){
        if(!retMode){
             d.getCond().accept(this);
             if((boolean)stk.pop()){
                 d.getThn().accept(this);
             }else{
                 if(d.getEls() != null){
                     d.getEls().accept(this);
                 }
             }
        }
    }

    public void visit(Return d){
         if(!retMode){
             d.getExp().accept(this);
             retMode = true;
         }
    }

    public void visit(Print d){
         if(!retMode){
             d.getExp().accept(this);
             System.out.println(stk.pop().toString());
         }
    }

    public void visit(BinOp e){ }

    public void visit(Sub  e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie - id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie - id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }
    public void visit(Plus e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie + id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie + id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         };
    }
    public void visit(Times e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie * id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie * id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }
    public void visit(Div e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie / id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie / id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }

    public void visit(Lte e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie <= id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie <= id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }


    public void visit(Lt e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie < id);
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie < id);
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }
    public void visit(Eq e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         if(stk.peek() instanceof Integer){
             Integer id = (Integer)stk.pop();
             if(stk.peek() instanceof Integer){
                Integer ie = (Integer)stk.pop();
                stk.push(ie.equals(id));
             }
         }else if(stk.peek() instanceof Float){
             Float id = (Float)stk.pop();
             if(stk.peek() instanceof Float){
                Float ie = (Float)stk.pop();
                stk.push(ie.equals(id));
             }
         }else if(stk.peek() instanceof Boolean){
             Boolean id = (Boolean)stk.pop();
             if(stk.peek() instanceof Boolean){
                Boolean ie = (Boolean)stk.pop();
                stk.push(ie.equals(id));
             }
         }else{
            throw new RuntimeException("Operção não ptermitida entre os tipos "  + e.getLine() + ", " + e.getCol() + ".");
         }
    }

    public void visit(Var e){
            Object val = env.read(e.getName());
            if(val != null){
                stk.push(val);
            }else{
               throw new RuntimeException("Variáve não declarada "  + e.getLine() + ", " + e.getCol() + " : " + e.getName());
            }
    }

    public void visit(FCall e){
        FunDef called = fn.get(e.getID());
        if(called != null){
           for(int j =  e.getArgs().size()-1; j >= 0; j--){
                e.getArgs().get(j).accept(this);
           }
           Env env1 = env;
           env = new Env();
           for(Bind b : called.getParams()){
                env.store(b.getVar().getName(),stk.pop());
           }
           called.accept(this);
           retMode = false;
           env = env1;
        }else{
           throw new RuntimeException("Chamada a função não delcarada em " + e.getLine() + ", " + e.getCol() + " : "  + e.getID());
        }
    }

    public void visit(IntLit e){ stk.push(e.getValue()); }
    public void visit(BoolLit e){ stk.push(e.getValue()); }
    public void visit(FloatLit e){stk.push(e.getValue()); }

    public void visit(TyBool t){  }
    public void visit(TyInt t){ }
    public void visit(TyFloat t){ }

}
