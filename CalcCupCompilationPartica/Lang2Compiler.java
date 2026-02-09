import java.io.*;
import calc.parser.CalcLexer;
import calc.parser.Lang2Parser;
import java_cup.runtime.Symbol;

public class Lang2Compiler {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Lang2Compiler -syn <arquivo>");
            return;
        }

        String mode = "";
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
            Lang2Parser parser = new Lang2Parser(lexer); // Instancia o parser novo

            if (mode.equals("-syn")) {
                parser.parse();
                System.out.println("accepted");
            } else {
                // Outros modos...
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

    private static void printHelp() {
        System.out.println("Uso: java CalcCompiler [opcao] <arquivo>");
        System.out.println("Opcoes:");
        System.out.println("  -syn   : Executa apenas a analise sintatica");
        System.out.println("  -i     : Executa o interpretador");
        System.out.println("  -v     : Mostra a versao e autores");
    }
}