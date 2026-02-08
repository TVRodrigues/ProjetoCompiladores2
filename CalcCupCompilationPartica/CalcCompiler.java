
import java.io.*;
import java_cup.runtime.*;
import calc.nodes.dotutils.DotFile;
import calc.nodes.CNode;
import calc.nodes.environment.Env;
import calc.parser.CalcParser;
import calc.parser.CalcLexer;
import calc.parser.CalcParserSym;
import calc.parser.CalcParserSym;
import calc.nodes.visitors.SimpleVisitor;
import calc.nodes.visitors.GVizVisitor;
import calc.nodes.visitors.InterpVisitor;
import calc.nodes.visitors.tychkvisitor.TyChecker;
import calc.nodes.visitors.CTranslateVisitor;
import calc.nodes.visitors.codeGen.CodeGenVisitor;

public class CalcCompiler{

     public static void runLexer(CalcLexer lex)  throws IOException,Exception{
           Symbol tk = lex.nextToken();
           while(tk.sym != CalcParserSym.EOF){
               System.out.println("(" + tk.left + ","  + tk.right + ")" + tk.sym);
               tk = lex.nextToken();
           }
           System.out.println(tk.toString());
     }

    public static void runDot(CalcParser p,String fname)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              GVizVisitor gv = new GVizVisitor();
              root.accept(gv);
              String dotfname = fname.replaceFirst("\\.[^\\.]+$",".dot");
              System.out.println("Escrevendo saida para: " + dotfname);
              gv.saveDot(dotfname);
        }else{
              System.out.println("root was null !");
        }
    }
    public static void interpretAndType(CalcParser p)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              TyChecker tv = new TyChecker();
              root.accept(tv);
              InterpVisitor v = new InterpVisitor();
              root.accept(v);
        }else{
              System.out.println("root was null !");
        }
    }

    public static void translate(CalcParser p,String fname)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              TyChecker tv = new TyChecker();
              root.accept(tv);
              String cfname = fname.replaceFirst("\\.[^\\.]+$",".c");
              CTranslateVisitor viac = new CTranslateVisitor(cfname,tv);
              root.accept(viac);
              System.out.println("Escrevendo saida para: " + cfname);
              viac.writeCFile();

        }else{
              System.out.println("root was null !");
        }
    }

    public static void compile(CalcParser p,String fname)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              TyChecker tv = new TyChecker();
              root.accept(tv);
              String cfname = fname.replaceFirst("\\.[^\\.]+$",".nasm");
              CodeGenVisitor cgv = new CodeGenVisitor(tv.getTypeCtx(),tv.getTypeNodes());
              root.accept(cgv);
              cgv.printCode();
        }else{
              System.out.println("root was null !");
        }
    }

    public static void interpret(CalcParser p)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              InterpVisitor v = new InterpVisitor();
              root.accept(v);
        }else{
              System.out.println("root was null !");
        }
    }

    public static void interpretDebug(CalcParser p)  throws IOException,Exception{
        Symbol presult = p.parse();
        CNode root = (CNode)presult.value;
        if(root != null){
              InterpVisitor v = new InterpVisitor();
              root.accept(v);

        }else{
              System.out.println("root was null !");
        }
    }

     public static void main(String args[])  throws IOException,Exception {
          int fname = 0;
          if(args.length < 1 || args.length > 2){
             printHelp();
             //System.out.println("         :");
             System.exit(0);
          }else{
              if(args.length == 2){ fname = 1;}
              CalcLexer lex = new CalcLexer(new FileReader(args[fname]));
              CalcParser p = new CalcParser(lex);
              if(args.length == 2 && args[0].equals("-lex")){
                    runLexer(lex);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-dot")){
                    runDot(p,args[fname]);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-ty")){
                    interpretAndType(p);
                    System.exit(0);
              }
              else if(args.length == 2 && args[0].equals("-i")){
                    interpret(p);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-id")){
                    interpretDebug(p);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-viac")){
                    translate(p,args[fname]);
                    System.exit(0);
              }else if(args.length == 2 && args[0].equals("-c")){
                    compile(p,args[fname]);
                    System.exit(0);
              }
          }
     }

     public static void printHelp(){
             System.out.println("use java CalcCompiler [opcao] <nome-de-arquivo>");
             System.out.println("opcao: ");
             System.out.println("   -lex  : lista os toke ");
             System.out.println("   -dot  : gera um arquivo dot contendo uma representação da AST.");
             System.out.println("   -i    : Interpreta o programa.");
             System.out.println("   -ti   : Verifica Tipos e interpreta.");
             System.out.println("   -id   : Interpreta o programa e imprime a tabela de ambiente de execução.");
             System.out.println("   -viac : Verifica tipos, e se o programa estiver correto gera código em C.");
             System.out.println("   -c    : Verifica tipos, e se o programa estiver correto gera código NASM p/ arquitetura X86_64.");
     }
}
