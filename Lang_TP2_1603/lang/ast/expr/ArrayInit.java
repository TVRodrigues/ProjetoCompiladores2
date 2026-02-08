package lang.ast.expr;

import lang.ast.NodeVisitor;
import lang.ast.types.LType;

public class ArrayInit extends Exp {
    private LType type;  // O tipo do array (ex.: Int, Float)
    private Exp size;   // A expressão que representa o tamanho do array

    public ArrayInit(LType type, Exp size) {
        super(0,0);
        this.type = type;
        this.size = size;
    }

    // Retorna o tipo do array
    public LType getType() {
        return type;
    }

    // Retorna o tamanho do array
    public Exp getSize() {
        return size;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this); // Permite que o visitante processe a inicialização do array
    }
}
