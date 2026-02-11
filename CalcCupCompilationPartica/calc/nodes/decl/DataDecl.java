package calc.nodes.decl;

import calc.nodes.CNode;
import calc.nodes.CalcVisitor;
import java.util.List;

public class DataDecl extends CNode {
    public String name;
    public List<Bind> binding;

    public DataDecl(int line, int col, String name, List<Bind> binding) {
        super(line, col);
        this.name = name;
        this.binding = binding;
    }

    @Override
    public String toString() {
        return "data " + name + " {...}";
    }

    @Override
    public void accept(CalcVisitor v) {
        v.visit(this);
    }
}