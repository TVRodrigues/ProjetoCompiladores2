package calc.nodes.visitors.codeGen;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;

import java.util.Hashtable;
import java.util.Stack;
import java.util.LinkedList;
import java.util.ArrayList;

import calc.nodes.visitors.tychkvisitor.*;


public  class CodeGenVisitor extends CalcVisitor{

    private Hashtable<String,TypeEntry> ctx;
    private Hashtable<String, VType> localCtx;
    private Hashtable<Object, VType> typeNode;
    private Hashtable<String, Integer> accCode;
    private CodeGen cg;

    public CodeGenVisitor   (Hashtable<String,TypeEntry> ctx, Hashtable<Object,VType> typeNode){
        this.ctx = ctx;
        this.typeNode =typeNode;
        cg = new CodeGen();
    }



    public VType typeOf(Object node){
       return typeNode.get(node);
    }

    public int sizeOf(VType ty){
        switch(ty.getTypeValue()){
            case CLTypes.INT :  return 8;
            case CLTypes.FLOAT: return 8;
            case CLTypes.BOOL:  return 1;
            case CLTypes.ARR:   return 8* sizeOf( ((VTyArr)ty).getTyArg() ) ;
        }
        return -1;
    }

    public void visit(Program p){
         cg.emit("call inicio");
         cg.emit("    ");
         cg.emit("mov rax, 60");
         cg.emit("mov rbx, 0");
         cg.emit("syscall");

         for(FunDef f : p.getFuncs()){
             int len = 0;
             localCtx = ctx.get(f.getFname()).localCtx;
             accCode = new Hashtable<String, Integer>();
             for(Bind b : f.getParams()){
                 String v = b.getVar().getName();
                 len = len + sizeOf(localCtx.get(v));
                 accCode.put(v,len-1);
                 System.out.println("; DEBUG " + v + " : " + (len-1));
             }
             for(java.util.Map.Entry<String,VType> ent : localCtx.entrySet()){
                 if(!accCode.contains(ent.getKey())){
                     len = len + sizeOf(localCtx.get(ent.getKey()));
                     accCode.put(ent.getKey(), len-1);
                     System.out.println("; DEBUG " + ent.getKey() + " : " + (len-1));
                 }
             }
             cg.emit(f.getFname() + ":");
             cg.emit("push rbp");
             cg.emit("mov rbp, rsp");
             cg.emit("sub rsp, " + len);
             f.accept(this);
             cg.emit("add rsp, " + len);
             cg.emit("pop rbp");
             cg.emit("ret");
         }
    }

    public void visit(FunDef d){

        // d.getRet().accept(this);
        // for(Bind b: d.getParams()){
        //    b.accept(this);
        // }
        d.getBody().accept(this);

    }

    public void visit(Bind  d){
        // d.getType().accept(this);
        // d.getVar().accept(this);
    }

    public void visit(CSeq d){
          d.getLeft().accept(this);
          d.getRight().accept(this);

    }

    public void visit(CAttr d){
             d.getExp().accept(this);
             VType ty = typeNode.get(d.getExp());
             String addr = accCode.get(d.getVar().getName()) == 0 ? "[rbp]" : "[rbp - "+ accCode.get(d.getVar().getName()) +"]";
             if(ty != null){
                switch(ty.getTypeValue()){
                    case CLTypes.INT  : cg.emit("pop rax");
                                        cg.emit("mov "+ addr +", rax");
                                        break;
                    case CLTypes.FLOAT: cg.emit("pop rax");
                                        cg.emit("mov "+ addr +", rax");
                                        break;
                    case CLTypes.BOOL : cg.emit("mov rax, 0");
                                        cg.emit("mov BYTE al, [rsp]");
                                        cg.emit("inc rsp");
                                        cg.emit("mov "+ addr +", al");

                                        break;
                    case CLTypes.ARR  : cg.emit("pop rax");
                                        cg.emit("mov "+ addr +", rax");
                                        break;
                }
             }

    }

    public void visit(Loop d){
          String lbTeste = cg.newLabel("while") ,lbEnd = cg.newLabel("while");
          cg.emit(lbTeste + ":");
          d.getCond().accept(this);
          cg.emit("pop rax");
          cg.emit("cmp rax, 0");
          cg.emit("je " + lbEnd);
          d.getBody().accept(this);
          cg.emit("jmp " + lbTeste );
          cg.emit(lbEnd+":" );
          cg.emit("nop");
    }

    public void visit(If d){
          String lbElse = cg.newLabel("ifels") ,lbEnd = cg.newLabel("ifend");
          d.getCond().accept(this);
          cg.emit("mov rax, 0");
          cg.emit("mov BYTE al, [rsp]");
          cg.emit("inc rsp");
          cg.emit("cmp rax, 0");
          if(d.getEls() != null){
             cg.emit("je " + lbElse );
             d.getThn().accept(this);
             cg.emit("jmp " + lbEnd );
             cg.emit(lbElse+ ":");
             d.getEls().accept(this);
          }else{
             cg.emit("je " + lbEnd );
             d.getThn().accept(this);
          }
          cg.emit(lbEnd+ ":");
          cg.emit("nop");
    }

    public void visit(Return d){
         // d.getExp().accept(this);
    }

    public void visit(Print d){
         d.getExp().accept(this);
         cg.emit("pop eax");
         cg.emit("call _printInt");
    }

    public void visit(BinOp e){ }

    public void visit(Sub  e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         cg.emit("pop rbx");
         cg.emit("pop rax");
         cg.emit("sub rax, rbx");
         cg.emit("push rax");
    }

    public void visit(Plus e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         cg.emit("pop rbx");
         cg.emit("pop rax");
         cg.emit("add rax, rbx");
         cg.emit("push rax");
    }

    public void visit(Times e){
         e.getLeft().accept(this);
         e.getRight().accept(this);
         cg.emit("pop rbx");
         cg.emit("pop rax");
         cg.emit("imul rbx");
         cg.emit("push rax");
    }


    public void visit(Div e){
         // e.getLeft().accept(this);
         // e.getRight().accept(this);
    }

    public void visit(Lte e){
         // e.getLeft().accept(this);
         // e.getRight().accept(this);
    }


    public void visit(Lt e){
         // e.getLeft().accept(this);
         // e.getRight().accept(this);
    }

    public void visit(Eq e){

         e.getLeft().accept(this);
         e.getRight().accept(this);

         cg.emit("pop rbx");
         cg.emit("pop rax");
         cg.emit("cmp rax, rbx");

         cg.emit("pushf");
         cg.emit("mov WORD cx, [rsp]");
         cg.emit("add rsp, 2");
         cg.emit("and rcx, 0000000000000001B"); //Zero Flag and Carriage

         cg.emit("mov al, 127");
         cg.emit("mul cl");

         cg.emit("dec rsp");
         cg.emit("mov [rsp], al");


         //cg.emit("and ebx, 0000000001000000B"); // Carriage flag

    }


    public void visit(Var e){
         String addr = accCode.get(e.getName()) == 0 ? "[rbp]" : "[rbp - "+ accCode.get(e.getName()) +"]";
         VType ty = localCtx.get(e.getName());
         if(ty!= null){
              switch(ty.getTypeValue()){
                    case CLTypes.INT  : cg.emit("mov rax, " + addr );
                                        cg.emit("push rax ");
                                        break;
                    case CLTypes.FLOAT: cg.emit("mov rax, "+ addr +"");
                                        cg.emit("push rax ");
                                        break;
                    case CLTypes.BOOL:  cg.emit("mov rax, 0");
                                        cg.emit("mov al," + addr);
                                        cg.emit("dec rsp ");
                                        cg.emit("mov BYTE [rsp], al");
                                        break;
                    case CLTypes.ARR  :
                                        cg.emit("mov "+ addr +", rax");
                                        cg.emit("push rax ");
                                        break;
              }

         }

    }

    public void visit(FCall e){
    }

    public void visit(IntLit e){  cg.emit("push " + e.getValue()); }
    public void visit(BoolLit e){
         if(e.getValue()){
            cg.emit("dec rsp");
            cg.emit("mov BYTE [rsp], 127");
         }else{
            cg.emit("dec rsp");
            cg.emit("mov [rsp]0");
         }

    }
    public void visit(FloatLit e){ }

    public void visit(TyBool t){  }
    public void visit(TyInt t){   }
    public void visit(TyFloat t){ }

    public void printCode(){
        cg.printCode();
    }

    // Adicione no final do CodeGenVisitor
    public void visit(Mod e) {}
    public void visit(And e) {}
    public void visit(Neq e) {}
    public void visit(Not e) {}
    public void visit(DataDecl d) {}
    public void visit(Iterate d) {}
    public void visit(Read d) {}
    public void visit(New e) {}
    public void visit(TyVoid t) {}
    public void visit(TyChar t) {}
    public void visit(TyArr t) {}
    public void visit(TyId t) {}

}
