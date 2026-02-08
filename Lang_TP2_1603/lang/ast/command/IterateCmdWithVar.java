package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.Var;

public class IterateCmdWithVar extends Cmd {
    private Var iterator;  // Variável de iteração (ex.: i)
    private Exp condition; // Condição do loop (ex.: 10)
    private Cmd body;      // Corpo do loop

    public IterateCmdWithVar(int left, int right, Var iterator, Exp condition, Cmd body) {
        super(left, right);
        this.iterator = iterator;
        this.condition = condition;
        this.body = body;
    }

    public Var getIterator() {
        return iterator;
    }

    public Exp getCondition() {
        return condition;
    }

    public Cmd getBody() {
        return body;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
