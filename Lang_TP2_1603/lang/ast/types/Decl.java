package lang.ast.types;


import lang.ast.NodeVisitor;

public class Decl extends LType {
    private String id;  // Nome da variável
    private LType type; // Tipo da variável

    public Decl(int left, int right, String id, LType type) {
        super(left, right);
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public LType getType() {
        return type;
    }


    public String toString() {
        return id + " :: " + type.toString();
    }
    
    
    public void accept(NodeVisitor v){
        v.visit(this);
    }


}
