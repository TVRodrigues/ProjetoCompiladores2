import java.io.*;
import calc.parser.CalcLexer;
import calc.parser.CalcParser;
import java_cup.runtime.Symbol;

public class CalcCompiler {

    public static void main(String[] args) {
   
        if (args.length == 0) {
            printHelp();
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

       
        if (args.length == 1) {
            mode = "default"; 
            fileName = args[0];
        } else if (args.length == 2) {
            mode = args[0];
            fileName = args[1];
        } else {
            System.out.println("Erro: Número inválido de argumentos.");
            printHelp();
            return;
        }

        
        try {
            
            CalcLexer lexer = new CalcLexer(new FileReader(fileName));
            CalcParser parser = new CalcParser(lexer);

            if (mode.equals("-syn")) {
                
                parser.parse(); 
                
                System.out.println("accepted");
            } 
            else if (mode.equals("-i")) {
                
                Symbol s = parser.parse();
                System.out.println("Interpretador ainda não implementado (Atividade futura).");
            } 
            else {
                System.out.println("Modo não reconhecido ou padrão: " + mode);
                parser.parse();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + fileName);
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