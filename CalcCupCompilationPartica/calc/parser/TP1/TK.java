/* 21/11/2024 - Lang_2 lexer
 * Thiago V. Rodrigues
 * 15.2.8140
 */
public enum TK {
     
     // Palavras Reservadas
     DATA, 
     INT, 
     CHAR, 
     BOOL, 
     FLOAT, 
     VOID, 
     IF, 
     ELSE, 
     ITERATE,
     READ, 
     PRINT, 
     RETURN, 
     NEW, 
     NULL, 
     TRUE, 
     FALSE, 
     INSTANCE, 
     CLASS,
     FOR,

     // Identificadores e Literais
     ID,
     TYID,
     INT_LIT,
     FLOAT_LIT,
     CHAR_LIT,

     // Operadores e Pontuação
     LPAREN, 
     RPAREN,
     LBRACKET,
     RBRACKET,
     LBRACE,
     RBRACE,
     COMMA,
     SEMI,
     COLON,
     DOT,
     DOUBLE_COLON,
     ARROW,
     EQUALS,
     
     // Operadores Aritméticos e Lógicos
     AND,
     PLUS,
     MINUS,
     TIMES,
     DIV,
     MOD,
     TYPE_AND,
     NOT,
     LESS,
     EQ,
     NEQ,

     EOF
}

