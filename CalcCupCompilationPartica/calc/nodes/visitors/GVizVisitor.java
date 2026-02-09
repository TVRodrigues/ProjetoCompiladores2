package calc.nodes.visitors;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.dotutils.DotFile;

public  class GVizVisitor extends CalcVisitor{


    private DotFile gf;
    private int subNode;

    public GVizVisitor(){
        gf = new DotFile();
    }

    public void saveDot(String filename){
         gf.writeToFile(filename);
    }

    public void visit(Program p){
         int root = gf.addNode("Program");
         for(FunDef f : p.getFuncs()){
             f.accept(this);
             gf.addEdge(root,subNode);
         }
         subNode = root;
    }

    public void visit(FunDef d){
        int root = gf.addNode("FunDef : " + d.getFname());
        d.getRet().accept(this);
        gf.addEdge(root, subNode);
        for(Bind b: d.getParams()){
           b.accept(this);
           gf.addEdge(root, subNode);
        }
        d.getBody().accept(this);
        gf.addEdge(root,subNode);
        subNode = root;
    }

    public void visit(Bind  d){
        int root = gf.addNode("Bind");
        d.getType().accept(this);
        gf.addEdge(root,subNode);

        d.getVar().accept(this);
        gf.addEdge(root,subNode);
        subNode = root;
    }

    public void visit(CSeq d){
          int root = gf.addNode("Seq");
          d.getLeft().accept(this);
          gf.addEdge(root, subNode);
          d.getRight().accept(this);
          gf.addEdge(root,subNode);
          subNode = root;
    }

    public void visit(CAttr d){
          int root = gf.addNode("Attr");
          d.getVar().accept(this);
          gf.addEdge(root, subNode);
          d.getExp().accept(this);
          gf.addEdge(root,subNode);
          subNode=  root;
    }

    public void visit(Loop d){
          int root = gf.addNode("Loop");
          d.getCond().accept(this);
          gf.addEdge(root, subNode);
          d.getBody().accept(this);
          gf.addEdge(root,subNode);
          subNode=  root;
    }
    public void visit(If d){
          int root = gf.addNode("If");
          d.getCond().accept(this);
          gf.addEdge(root,subNode);
          d.getThn().accept(this);
          gf.addEdge(root,subNode);
          if(d.getEls() != null){
             d.getEls().accept(this);
             gf.addEdge(root,subNode);
          }
          subNode = root;
    }

    public void visit(Return d){
         int root = gf.addNode("Return");
         d.getExp().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }
    public void visit(Print d){
         int root = gf.addNode("Print");
         d.getExp().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(BinOp e){ }

    public void visit(Sub  e){
         int root = gf.addNode("-");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Plus e){
         int root = gf.addNode("+");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Times e){
         int root = gf.addNode("*");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Div e){
         int root = gf.addNode("/");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Lte e){
         int root = gf.addNode("<=");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Lt e){
         int root = gf.addNode("<");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Eq e){
         int root = gf.addNode("==");
         e.getLeft().accept(this);
         gf.addEdge(root,subNode);
         e.getRight().accept(this);
         gf.addEdge(root,subNode);
         subNode = root;
    }

    public void visit(Var e){
        subNode = gf.addNode(e.getName());
    }


    public void visit(FCall e){
        int root = gf.addNode("FCall: " + e.getID());
        for(Exp ex : e.getArgs()){
             ex.accept(this);
             gf.addEdge(root,subNode);
        }
        subNode = root;
    }

    public void visit(IntLit e){  subNode = gf.addNode(e.getValue()+""); }
    public void visit(BoolLit e){ subNode = gf.addNode(e.getValue()+""); }
    public void visit(FloatLit e){subNode = gf.addNode(e.getValue()+""); }

    public void visit(TyBool t){ subNode = gf.addNode("TyBool"); }
    public void visit(TyInt t){  subNode = gf.addNode("TyInt");  }
    public void visit(TyFloat t){subNode = gf.addNode("TyFloat");}

    // Implementações vazias para os novos nós do Lang2
    public void visit(Mod e) { printNode(e.hashCode(), "Mod %"); e.left.accept(this); e.right.accept(this); }
    public void visit(And e) { printNode(e.hashCode(), "And &&"); e.left.accept(this); e.right.accept(this); }
    public void visit(Neq e) { printNode(e.hashCode(), "Neq !="); e.left.accept(this); e.right.accept(this); }
    public void visit(Not e) { printNode(e.hashCode(), "Not !"); e.arg.accept(this); }
    
    public void visit(DataDecl d) { printNode(d.hashCode(), "Data " + d.typeName); }
    public void visit(Iterate d) { printNode(d.hashCode(), "Iterate"); d.body.accept(this); }
    public void visit(Read d) { printNode(d.hashCode(), "Read"); }
    public void visit(New e) { printNode(e.hashCode(), "New " + e.type); }
    
    public void visit(TyVoid t) { printNode(t.hashCode(), "Void"); }
    public void visit(TyChar t) { printNode(t.hashCode(), "Char"); }
    public void visit(TyArr t) { printNode(t.hashCode(), "Arr[]"); }
    public void visit(TyId t) { printNode(t.hashCode(), "Type " + t.name); }

}
