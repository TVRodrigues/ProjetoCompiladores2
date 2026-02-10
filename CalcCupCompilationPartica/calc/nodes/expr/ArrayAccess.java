package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class ArrayAccess extends Exp {
    public Exp array; // O vetor (ex: 'v' ou 'v[0]')
    public Exp index; // O índice (ex: 0, i, ou i+1)

    public ArrayAccess(int line, int col, Exp array, Exp index) {
        super(line, col);
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}