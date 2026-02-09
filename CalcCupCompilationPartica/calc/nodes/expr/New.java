package calc.nodes.expr;

import calc.nodes.CalcVisitor;
import calc.nodes.types.CType;

public class New extends Exp {
    public CType type;
    public Exp size; 

    // CORREÇÃO: Adicionado line e col
    public New(int line, int col, CType type, Exp size) {
        super(line, col);
        this.type = type;
        this.size = size;
    }

    @Override
    public String toString() {
        return "new " + type + (size != null ? "[" + size + "]" : "");
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}