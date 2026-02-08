package lang.ast.types;


import java.util.List;
import lang.ast.NodeVisitor;

public class DataDecl extends LType {


    private String typeId; // Nome do novo tipo de dado
    private List<Decl> decls; // Lista de declarações dentro do tipo

    public DataDecl(int left, int right, String typeId, List<Decl> decls) {
        super(left, right);
        this.typeId = typeId;
        this.decls = decls;
    }

    public String getTypeId() {
        return typeId;
    }

    public List<Decl> getDecls() {
        return decls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("data ").append(typeId).append(" {\n");
        for (Decl d : decls) {
            sb.append("  ").append(d.toString()).append(";\n");
        }
        sb.append("}");
        return sb.toString();
    }


    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}

