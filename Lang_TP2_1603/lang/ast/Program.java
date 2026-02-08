// package lang.ast;

// // import lang.ast.dotutils.DotFile;
// import lang.ast.environment.Env;
// import java.util.ArrayList;
// import lang.ast.decl.FunDef;

// public class Program extends Node{

//    private ArrayList<FunDef> funcs;


//    public Program(int l, int c, ArrayList<FunDef> fs){
//        super(l,c);
//        this.funcs = fs;
//    }

//    public ArrayList<FunDef> getFuncs(){return funcs;}

//    public void accept(NodeVisitor v){v.visit(this);}

// }

// package lang.ast;

// import java.util.List;
// import lang.ast.command.Cmd;

// public class Program extends Cmd {
//     private List<Cmd> commands;

//     public Program(List<Cmd> commands) {
//         super(0, 0);
//         this.commands = commands;
//     }

//     public List<Cmd> getCommands() {
//         return commands;
//     }
// }
