/* 21/11/2024 - Lang_2 lexer
 * Thiago V. Rodrigues
 * 15.2.8140
 */
import java.io.*;


public class LangCompiler{

    public static void runLexer(String fileName) throws IOException {
        LangLexer lexer = new LangLexer(new FileReader(fileName));
        
        Token t;
        int count = 1;
        
        // Loop até encontrar EOF
        do {
            t = (Token) lexer.nextToken();
            if (t.tk != TK.EOF) {
                // Formato: Indice (Linha,Coluna) Lexema
                System.out.printf("%d (%d,%d) %s%n", 
                                  count++, 
                                  t.line() + 1, 
                                  t.col() + 1, 
                                  t.val().toString());
            }
        } while (t.tk != TK.EOF);
    }

     public static void main(String args[]) throws IOException {
        if (args.length < 2) {
            System.out.println("Uso: java LangCompiler -lex <arquivo>");
            System.exit(0);
        }

        String option = args[0];
        String filename = args[1];

        if (option.equals("-lex")) {
            runLexer(filename);
        } else {
            // Por enquanto, apenas -lex está implementado para o TP1
            System.out.println("Opcao nao implementada ou desconhecida: " + option);
        }
    }
}

