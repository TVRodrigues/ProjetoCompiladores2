package calc.parser;

import java_cup.runtime.Symbol;

%%

%public
%class CalcLexer
%type Symbol
%function nextToken
%line
%column
%unicode

%eofval{
   return new Symbol(Lang2ParserSym.EOF, yyline + 1, yycolumn + 1);
%eofval}

%{
    private int toInt(String s){
       try{ return Integer.parseInt(s); } catch(Exception e){ return 0; }
    }
    private float toFloat(String s){
       try{ return Float.parseFloat(s); } catch(Exception e){ return 0; }
    }
%}

white       = [ \t\r\n]+
lineComment = "--" [^\r\n]*
blockComment= "{-" ~"-}"

digit = [0-9]
lower = [a-z]
upper = [A-Z]
alpha = [a-zA-Z_]
id    = {lower}({alpha}|{digit})*
tyid  = {upper}({alpha}|{digit})*

int   = {digit}+
float = {digit}+"."{digit}+

char_content = [^\\\'\r\n] | "\\" [ntbr\\\'\"] | "\\" [0-9]{3}
char_lit     = "'" {char_content} "'"

%%

<YYINITIAL>{
    {white}         { /* Ignora */ }
    {lineComment}   { /* Ignora */ }
    {blockComment}  { /* Ignora */ }

    "data"      { return new Symbol(Lang2ParserSym.DATA, yyline+1, yycolumn+1); }
    "iterate"   { return new Symbol(Lang2ParserSym.ITERATE, yyline+1, yycolumn+1); }
    "if"        { return new Symbol(Lang2ParserSym.IF, yyline+1, yycolumn+1); }
    "else"      { return new Symbol(Lang2ParserSym.ELSE, yyline+1, yycolumn+1); }
    "return"    { return new Symbol(Lang2ParserSym.RETURN, yyline+1, yycolumn+1); }
    "new"       { return new Symbol(Lang2ParserSym.NEW, yyline+1, yycolumn+1); }
    "print"     { return new Symbol(Lang2ParserSym.PRINT, yyline+1, yycolumn+1); }
    "read"      { return new Symbol(Lang2ParserSym.READ, yyline+1, yycolumn+1); }
    "null"      { return new Symbol(Lang2ParserSym.NULL, yyline+1, yycolumn+1); }
    
    "Int"       { return new Symbol(Lang2ParserSym.TYINT, yyline+1, yycolumn+1); }
    "Char"      { return new Symbol(Lang2ParserSym.TYCHAR, yyline+1, yycolumn+1); }
    "Bool"      { return new Symbol(Lang2ParserSym.TYBOOL, yyline+1, yycolumn+1); }
    "Float"     { return new Symbol(Lang2ParserSym.TYFLOAT, yyline+1, yycolumn+1); }
    "Void"      { return new Symbol(Lang2ParserSym.VOID, yyline+1, yycolumn+1); }

    "true"      { return new Symbol(Lang2ParserSym.TRUE, yyline+1, yycolumn+1, true); }
    "false"     { return new Symbol(Lang2ParserSym.FALSE, yyline+1, yycolumn+1, false); }

    "("         { return new Symbol(Lang2ParserSym.LPAREN, yyline+1, yycolumn+1); }
    ")"         { return new Symbol(Lang2ParserSym.RPAREN, yyline+1, yycolumn+1); }
    "["         { return new Symbol(Lang2ParserSym.LBRACKET, yyline+1, yycolumn+1); }
    "]"         { return new Symbol(Lang2ParserSym.RBRACKET, yyline+1, yycolumn+1); }
    "{"         { return new Symbol(Lang2ParserSym.LBRACE, yyline+1, yycolumn+1); }
    "}"         { return new Symbol(Lang2ParserSym.RBRACE, yyline+1, yycolumn+1); }
    ","         { return new Symbol(Lang2ParserSym.COMMA, yyline+1, yycolumn+1); }
    ";"         { return new Symbol(Lang2ParserSym.SEMI, yyline+1, yycolumn+1); }
    ":"         { return new Symbol(Lang2ParserSym.COLON, yyline+1, yycolumn+1); }
    "."         { return new Symbol(Lang2ParserSym.DOT, yyline+1, yycolumn+1); }
    "::"        { return new Symbol(Lang2ParserSym.DCOLON, yyline+1, yycolumn+1); }
    "->"        { return new Symbol(Lang2ParserSym.ARROW, yyline+1, yycolumn+1); }
    "="         { return new Symbol(Lang2ParserSym.ASSIGN, yyline+1, yycolumn+1); }
    "&&"        { return new Symbol(Lang2ParserSym.AND, yyline+1, yycolumn+1); }
    "&"         { return new Symbol(Lang2ParserSym.TYPE_AND, yyline+1, yycolumn+1); }
    "!"         { return new Symbol(Lang2ParserSym.NOT, yyline+1, yycolumn+1); }
    "<"         { return new Symbol(Lang2ParserSym.LT, yyline+1, yycolumn+1); }
    /* CORREÇÃO: GT adicionado */
    ">"         { return new Symbol(Lang2ParserSym.GT, yyline+1, yycolumn+1); }
    "=="        { return new Symbol(Lang2ParserSym.EQ, yyline+1, yycolumn+1); }
    "!="        { return new Symbol(Lang2ParserSym.NEQ, yyline+1, yycolumn+1); }
    "+"         { return new Symbol(Lang2ParserSym.PLUS, yyline+1, yycolumn+1); }
    "-"         { return new Symbol(Lang2ParserSym.MINUS, yyline+1, yycolumn+1); }
    "*"         { return new Symbol(Lang2ParserSym.TIMES, yyline+1, yycolumn+1); }
    "/"         { return new Symbol(Lang2ParserSym.DIV, yyline+1, yycolumn+1); }
    "%"         { return new Symbol(Lang2ParserSym.MOD, yyline+1, yycolumn+1); }

    {tyid}      { return new Symbol(Lang2ParserSym.TYID, yyline+1, yycolumn+1, yytext()); }
    {id}        { return new Symbol(Lang2ParserSym.ID, yyline+1, yycolumn+1, yytext()); }
    {int}       { return new Symbol(Lang2ParserSym.INT_LIT, yyline+1, yycolumn+1, toInt(yytext())); }
    {float}     { return new Symbol(Lang2ParserSym.FLOAT_LIT, yyline+1, yycolumn+1, toFloat(yytext())); }
    {char_lit}  { return new Symbol(Lang2ParserSym.CHAR_LIT, yyline+1, yycolumn+1, yytext()); }
    
    .           { throw new Error("Caractere ilegal <" + yytext() + "> na linha " + yyline); }
}