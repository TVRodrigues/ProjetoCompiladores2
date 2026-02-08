package lang.ast.types;


import java.util.List;
import lang.ast.NodeVisitor;
import lang.ast.command.Cmd;

public class Func extends LType {
    private String name; // Nome da função
    private List<Param> params; // Lista de parâmetros
    private LType returnType; // Tipo de retorno
    private List<Cmd> body; // Corpo da função (bloco de comandos)

    // Construtor
    public Func(int left, int right, String name, List<Param> params, LType returnType, List<Cmd> body) {
        super(left, right);
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    // Getters
    public String getId() {
        return name;
    }

    public List<Param> getParams() {
        return params;
    }

    public LType getType() {
        return returnType;
    }

    public List<Cmd> getBody() {
        return body;
    }


    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
