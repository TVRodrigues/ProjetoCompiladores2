import java.io.*;
import calc.parser.CalcLexer;
import calc.parser.Lang2Parser;
import calc.nodes.Program; 
import calc.nodes.visitors.InterpVisitor;
import java_cup.runtime.Symbol;

public class Lang2Compiler {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Lang2Compiler [-syn|-i|-v] <arquivo>");
            return;
        }

        String mode = "default";
        String fileName = "";

        if (args[0].equals("-v")) {
            System.out.println("LangV2-2025/2-v:0.1.2");
            System.out.println("15.2.8140"); 
            System.out.println("Thiago Vieira Rodrigues"); 
            return;
        }

        if (args.length == 2) {
             mode = args[0];
             fileName = args[1];
        } else {
             fileName = args[0];
        }

        try {
            CalcLexer lexer = new CalcLexer(new FileReader(fileName));
            Lang2Parser parser = new Lang2Parser(lexer);

            if (mode.equals("-syn")) {
                parser.parse();
                System.out.println("accepted");
            } 
            else if (mode.equals("-i")) {
                Symbol s = parser.parse();
                Program prog = (Program) s.value;
                if (prog != null) {
                    InterpVisitor interpreter = new InterpVisitor();
                    interpreter.visit(prog);
                }
            }
            else {
                parser.parse();
            }
        } catch (Exception e) {
            if (mode.equals("-syn")) {
                System.out.println("rejected");
            } else {
                e.printStackTrace();
            }
        }
    }
}