package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Diferente extends BinOp {

    public Diferente(int var1, int var2, Exp var3, Exp var4) {
       super(var1, var2, var3, var4);
    }
 
    public void accept(NodeVisitor v) {
       v.visit(this);
    }
 }