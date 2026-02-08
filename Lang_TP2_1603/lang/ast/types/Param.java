package lang.ast.types;

import lang.ast.NodeVisitor;

public class Param extends LType {

    private String name; // Nome do parâmetro
    private LType type; // Tipo do parâmetro

    public Param(int left, int right, String Id, LType type) {
        super(left, right);
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return name;
    }

    public LType getType() {
        return type;
    }



    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
