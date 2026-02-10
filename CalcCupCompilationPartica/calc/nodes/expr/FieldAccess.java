package calc.nodes.expr;
import calc.nodes.CalcVisitor;

public class FieldAccess extends Exp {
    public Exp exp;      // A expressão que é a struct (ex: 'p' ou 'vetor[0]')
    public String field; // O nome do campo (ex: 'x')

    public FieldAccess(int line, int col, Exp exp, String field) {
        super(line, col);
        this.exp = exp;
        this.field = field;
    }

    @Override
    public String toString() { return exp.toString() + "." + field; }

    @Override
    public void accept(CalcVisitor v) { v.visit(this); }
}