package lang.parser;

import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

%%

%public
%function nextToken
%type Symbol
%class LangLexer

%line
%column

%unicode

%eofval{
return new Symbol(LangParserSym.EOF, yyline + 1, yycolumn + 1);
%eofval}

%{
private ArrayList arr;

private int toInt(String s){
    try {
        return Integer.parseInt(yytext());
    } catch (NumberFormatException e) {
        System.out.println("Erro de conversão: " + s + " para inteiro");
        return 0;
    }
}

    private float toFloat(String s){
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            System.out.println("Erro de conversão: " + s + " para ponto flutuante");
            return 0;
        }
}


private char toChar(String s) {
    if (s.charAt(0) != '\'' || s.charAt(s.length() - 1) != '\'') {
        throw new Error("Formato de caractere inválido: " + s);
    }

   String content = s.substring(1, s.length()-1);

    // Processa sequências de escape
    // Se o conteúdo começar com '\' é uma sequência de escape
    if (content.startsWith("\\")) {
        // Escape simples: deve ter exatamente 2 caracteres (ex.: '\n')
        if (content.length() == 2) {
            char esc = content.charAt(1);
            switch (esc) {
                case 'b': return '\b';   // backspace
                case 't': return '\t';   // tabulação
                case 'n': return '\n';   // quebra de linha
                case 'r': return '\r';   // carriage return
                case '\\': return '\\';  // barra invertida
                case '\'': return '\'';  // aspas simples
                case '\"': return '\"';  // aspas duplas
                default:
                    throw new Error("Sequência de escape desconhecida: " + esc);
            }
        // Escape numérico: deve ter exatamente 4 caracteres (ex.: '\065')
        } else if (content.length() == 4) {
            String digitos = content.substring(1); // Remove a barra invertida
            try {
                int code = Integer.parseInt(digitos);
                return (char) code;
            } catch (NumberFormatException e) {
                throw new Error("Código ASCII inválido: " + digitos);
            }
        } else {
            throw new Error("Escape sequence de tamanho inválido: " + content);
        }
    } else {
        // Se não for sequência de escape, deve ter exatamente um caractere
        if (content.length() != 1) {
            throw new Error("Formato de caractere inválido: " + s);
        }
        return content.charAt(0);
    }
}


private Symbol mkSymbol(int symCode, Object obj){
    return new Symbol(symCode, yyline + 1, yycolumn + 1, obj);
}

private Symbol mkSymbol(int symCode){
    return mkSymbol(symCode, null);
}

%}

identifier = [a-zA-Z_][a-zA-Z0-9_]*
number = [0-9]+
white = [ \n\t\r]+
comment_line = "--".*  // Comentário de uma linha
comment_multiline = "{-"[^-]*("-"[^}][^-]*)*"-}"
//char = \'(\\[btnr\"\'\\\\]|[^\'\\])\'
char = \'(\\([btnr\"\'\\\\]|[0-9]{3})|[^\'\\])\'
string = \"([^\"\\\\]|\\\\.)*\" // Reconhece strings entre aspas duplas
float = ({number}"."{number})
TYPEID = [A-Z][a-zA-Z0-9_]*

%%

<YYINITIAL>{
  "main" { return mkSymbol(LangParserSym.MAIN); }
  "data" { return mkSymbol(LangParserSym.DATA); }
  "param" { return mkSymbol(LangParserSym.DATA); }
  "if" { return mkSymbol(LangParserSym.IF); }
  "else" { return mkSymbol(LangParserSym.ELSE); }
  "iterate" { return new Symbol(LangParserSym.ITERATE, yyline + 1, yycolumn + 1); }
  "print" { return mkSymbol(LangParserSym.PRINT); }
  "return" { return mkSymbol(LangParserSym.RETURN); }
  "Void" { return mkSymbol(LangParserSym.VOID); }
  "true" { return mkSymbol(LangParserSym.TRUE, true); }
  "false" { return mkSymbol(LangParserSym.FALSE, false); }
  "null" { return mkSymbol(LangParserSym.NULL); }
  
  {TYPEID} { return mkSymbol(LangParserSym.TYID, yytext()); }
  {identifier} { return mkSymbol(LangParserSym.ID, yytext()); }

  {white} {/* Ignora espaços em branco */ }
  "::"          { return mkSymbol(LangParserSym.COLONCOLON); }
  ";"            { return mkSymbol(LangParserSym.PV); }
  "{"            { return mkSymbol(LangParserSym.LBRACE); }
  "}"            { return mkSymbol(LangParserSym.RBRACE); }
  ":"            { return mkSymbol(LangParserSym.COLON); }
  "("            { return mkSymbol(LangParserSym.LP); }
  ")"            { return mkSymbol(LangParserSym.RP); }
  ","            { return mkSymbol(LangParserSym.COMMA); }
  "+"            { return mkSymbol(LangParserSym.PLUS); }
  "-"            { return mkSymbol(LangParserSym.MINUS); }
  "*"            { return mkSymbol(LangParserSym.TIMES); }
  "/"            { return mkSymbol(LangParserSym.DIV); }
  "%"            { return mkSymbol(LangParserSym.MOD); }  
  "<"            { return mkSymbol(LangParserSym.MENORQ); }
  ">"            { return mkSymbol(LangParserSym.MAIORQ); }
  "=="           { return mkSymbol(LangParserSym.IGUAL); }
  "!="           { return mkSymbol(LangParserSym.DIFERENTE); }
  "&&"           { return mkSymbol(LangParserSym.AND); }
  "!"            { return mkSymbol(LangParserSym.NOT); }
  "="            { return mkSymbol(LangParserSym.ATTRIB); }
  "["            { return mkSymbol(LangParserSym.LBRACKET); }
  "]"            { return mkSymbol(LangParserSym.RBRACKET); }
  
  {string}       { return mkSymbol(LangParserSym.STRING, yytext().substring(1, yytext().length() - 1)); }
  {number}       { return mkSymbol(LangParserSym.NUMBER, toInt(yytext())); }
  {char}         { return mkSymbol(LangParserSym.CHAR, toChar(yytext())); }
  {float}        { return mkSymbol(LangParserSym.FLOAT, toFloat(yytext())); }
  
  {comment_line}      { /* Ignora comentário de linha */ }
  {comment_multiline} { /* Ignora comentário multi-linha */ }

  [^] { throw new Error("Caracter ilegal <" + yytext() + ">"); }
}
