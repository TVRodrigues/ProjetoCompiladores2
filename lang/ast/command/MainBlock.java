package lang.ast.command;

import java.util.List;
import lang.ast.NodeVisitor;
import lang.ast.types.*;

public class MainBlock extends Cmd {

    private List<Cmd> cmds;

    private List<DataDecl> dataDecls; // Declarações de dados
    private List<Func> funcs; // Funções

    public MainBlock(List<DataDecl> dataDecls, List<Func> funcs, List<Cmd> cmds) {
        super(0, 0);
        this.dataDecls = dataDecls;
        this.funcs = funcs;
        this.cmds = cmds;
    }

    public List<DataDecl> getDataDecls() {
        return dataDecls;
    }

    public List<Func> getFuncs() {
        return funcs;
    }

    public List<Cmd> getCmds() {
        return cmds;
    }

    public void accept(NodeVisitor visitor) {
        // visitor.visit(this);
        for (Cmd cmd : cmds) {
            cmd.accept(visitor);
        }
    }


}
