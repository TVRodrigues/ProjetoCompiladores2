/* 21/11/2024 - Lang_2 lexer
 * Thiago V. Rodrigues
 * 15.2.8140
 */

import java.util.ArrayList;
import plib.VirtualLexer;
import plib.VirtualToken;
%%

%public
%class LangLexer
%implements VirtualLexer
%type Token
%function nextToken
%line
%column
%unicode


%eofval{
   return new Token(yyline, yycolumn, TK.EOF, null);
%eofval}

// Macros
digit = [0-9]
lower = [a-z]
upper = [A-Z]
alpha = [a-zA-Z_]
id    = {lower}({alpha}|{digit})*
tyid  = {upper}({alpha}|{digit})*
int   = {digit}+
float = {digit}+"."{digit}+
white = [ \t\r\n]+
char_content = [^\\\'\r\n] | "\\" [ntbr\\\'\"] | "\\" [0-9]{3}
char_lit     = "'" {char_content} "'"
lineComment = "--" [^\r\n]*
blockComment = "{-" ~"-}"


%%
<YYINITIAL>{
    {white}       { /* Ignora espaços */ }
    {lineComment}   { /* Ignora comentários de linha */ }
    {blockComment}  { /* Ignora comentários de bloco */ }
    // Palavras reservadas
    "data"      { return new Token(yyline, yycolumn, TK.DATA, yytext()); }
    "Int"       { return new Token(yyline, yycolumn, TK.INT, yytext()); }
    "Char"      { return new Token(yyline, yycolumn, TK.CHAR, yytext()); }
    "Bool"      { return new Token(yyline, yycolumn, TK.BOOL, yytext()); }
    "Float"     { return new Token(yyline, yycolumn, TK.FLOAT, yytext()); }
    "Void"      { return new Token(yyline, yycolumn, TK.VOID, yytext()); }
    "if"        { return new Token(yyline, yycolumn, TK.IF, yytext()); }
    "else"      { return new Token(yyline, yycolumn, TK.ELSE, yytext()); }
    "iterate"   { return new Token(yyline, yycolumn, TK.ITERATE, yytext()); }
    "read"      { return new Token(yyline, yycolumn, TK.READ, yytext()); }
    "print"     { return new Token(yyline, yycolumn, TK.PRINT, yytext()); }
    "return"    { return new Token(yyline, yycolumn, TK.RETURN, yytext()); }
    "new"       { return new Token(yyline, yycolumn, TK.NEW, yytext()); }
    "null"      { return new Token(yyline, yycolumn, TK.NULL, yytext()); }
    "true"      { return new Token(yyline, yycolumn, TK.TRUE, yytext()); }
    "false"     { return new Token(yyline, yycolumn, TK.FALSE, yytext()); }
    "instance"  { return new Token(yyline, yycolumn, TK.INSTANCE, yytext()); }
    "class"     { return new Token(yyline, yycolumn, TK.CLASS, yytext()); }
    "for"       { return new Token(yyline, yycolumn, TK.FOR, yytext()); }
    // Operadores
    "("          { return new Token(yyline, yycolumn, TK.LPAREN, yytext()); }
    ")"          { return new Token(yyline, yycolumn, TK.RPAREN, yytext()); }
    "["          { return new Token(yyline, yycolumn, TK.LBRACKET, yytext()); }
    "]"          { return new Token(yyline, yycolumn, TK.RBRACKET, yytext()); }
    "{"          { return new Token(yyline, yycolumn, TK.LBRACE, yytext()); }
    "}"          { return new Token(yyline, yycolumn, TK.RBRACE, yytext()); }
    ","          { return new Token(yyline, yycolumn, TK.COMMA, yytext()); }
    ";"          { return new Token(yyline, yycolumn, TK.SEMI, yytext()); }
    ":"          { return new Token(yyline, yycolumn, TK.COLON, yytext()); }
    "."          { return new Token(yyline, yycolumn, TK.DOT, yytext()); }
    "::"         { return new Token(yyline, yycolumn, TK.DOUBLE_COLON, yytext()); }
    "->"         { return new Token(yyline, yycolumn, TK.ARROW, yytext()); }
    "="          { return new Token(yyline, yycolumn, TK.EQUALS, yytext()); }
    "&&"         { return new Token(yyline, yycolumn, TK.AND, yytext()); }
    "&"          { return new Token(yyline, yycolumn, TK.TYPE_AND, yytext()); }
    "!"          { return new Token(yyline, yycolumn, TK.NOT, yytext()); }
    "<"          { return new Token(yyline, yycolumn, TK.LESS, yytext()); }
    "=="         { return new Token(yyline, yycolumn, TK.EQ, yytext()); }
    "!="         { return new Token(yyline, yycolumn, TK.NEQ, yytext()); }
    "+"          { return new Token(yyline, yycolumn, TK.PLUS, yytext()); }
    "-"          { return new Token(yyline, yycolumn, TK.MINUS, yytext()); }
    "*"          { return new Token(yyline, yycolumn, TK.TIMES, yytext()); }
    "/"          { return new Token(yyline, yycolumn, TK.DIV, yytext()); }
    "%"          { return new Token(yyline, yycolumn, TK.MOD, yytext()); }
    // Identificadores e Literais
    {id}          { return new Token(yyline, yycolumn, TK.ID, yytext()); }
    {tyid}        { return new Token(yyline, yycolumn, TK.TYID, yytext()); }
    {int}         { return new Token(yyline, yycolumn, TK.INT_LIT, yytext()); }
    {float}       { return new Token(yyline, yycolumn, TK.FLOAT_LIT, yytext()); }
    {char_lit}    { return new Token(yyline, yycolumn, TK.CHAR_LIT, yytext()); }
    // Erro
    .             { throw new Error("Caractere ilegal <" + yytext() + ">"); }
}


