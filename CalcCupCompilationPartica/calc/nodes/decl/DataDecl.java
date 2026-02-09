package calc.nodes.decl;

import calc.nodes.CNode;
import calc.nodes.CalcVisitor;
import java.util.List;

public class DataDecl extends CNode {
    public String typeName;
    public List<Bind> fields;

    // CORREÇÃO: Adicionado line e col ao construtor e ao super()
    public DataDecl(int line, int col, String typeName, List<Bind> fields) {
        super(line, col);
        this.typeName = typeName;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "data " + typeName + " {...}";
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}