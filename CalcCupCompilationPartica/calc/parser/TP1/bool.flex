/* 14/11/2024 - Calc lexer
 * Elton M. Cardoso
 * Example of a lexer for a simple claculator language.
 *
 * This lexer implementation contains Arrays as tokens.
 * The students are encouraged to argue why this is a bad ideia !
 *
 */

import java.util.ArrayList;
import plib.VirtualLexer;
import plib.VirtualToken;
%%

%public
%function nextToken
%type VirtualToken
%class BoolLexer
%implements VirtualLexer

%line
%column

%unicode

%eofval{
   return new Token( yyline, yycolumn, TK.EOF);
%eofval}



%{
       private ArrayList arr;

       private int toInt(String s){
          try{
              return Integer.parseInt(yytext());
          }catch(NumberFormatException e){
              System.out.println("Impossible error converting " + s + " to integer");
              return 0;
          }
       }

%}


identifier = [a-z]+
white =  [ \n\t\r]+ | {comment}
comment = "{-" ~"-}"


%%
<YYINITIAL>{
"--"  !([^]* \R [^]*) \R  {}

"t"            { return new Token (yyline, yycolumn, TK.TRUE); }
"&"            { return new Token( yyline, yycolumn, TK.AND);  }
"!"            { return new Token( yyline, yycolumn, TK.NOT); }
"="            { return new Token( yyline, yycolumn, TK.BIND); }
";"            { return new Token( yyline, yycolumn, TK.PV); }
"let"          { return new Token( yyline, yycolumn, TK.LET); }
"in"           { return new Token( yyline, yycolumn, TK.IN); }
{identifier}   { return new Token(yyline, yycolumn, TK.ID, yytext()); }
{white}        {/* While reading whites do nothing*/ }
[^]            {/* Matches any char form the input*/
                throw new Error("Illegal character <"+ yytext()+">"); }
}


