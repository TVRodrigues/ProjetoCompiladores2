/* 14/11/2024 - Calc lexer
 * Elton M. Cardoso
 * Example of a lexer for a simple claculator language.
 *
 * This lexer implementation contains Arrays as tokens.
 * The students are encouraged to argue why this is a bad ideia !
 *
 */
package calc.parser;

import java.util.ArrayList;
import java_cup.runtime.Symbol;

%%

%public
%function nextToken
%type Symbol
%class CalcLexer


%line
%column

%unicode

%eofval{
   return new Symbol(CalcParserSym.EOF, yyline + 1, yycolumn + 1);
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

       private float toFloat(String s){
          try{
              return Float.parseFloat(yytext());
          }catch(NumberFormatException e){
              System.out.println("Impossible error converting " + s + " to float");
              return 0;
          }
       }

%}


identifier = [a-z]+
number = [0-9]+
float = [0-9]*\.[0-9]+
white =  [ \n\t\r]+ | {comment}
comment = "{-" ~"-}"


%%
<YYINITIAL>{
"--"  !([^]* \R [^]*) \R  {}
"Int"          { return new Symbol(CalcParserSym.TYINT, yyline + 1, yycolumn + 1); }
"Bool"         { return new Symbol(CalcParserSym.TYBOOL, yyline + 1, yycolumn + 1); }
"Float"        { return new Symbol(CalcParserSym.TYFLOAT, yyline + 1, yycolumn + 1); }
"true"         { return new Symbol(CalcParserSym.TRUE, yyline + 1, yycolumn + 1, true );   }
"false"        { return new Symbol(CalcParserSym.FALSE, yyline + 1, yycolumn + 1, false);   }
{identifier}   { return new Symbol(CalcParserSym.ID, yyline + 1, yycolumn + 1, yytext()); }
{number}       { return new Symbol(CalcParserSym.NUMBER, yyline + 1, yycolumn + 1, toInt(yytext()));   }
{float}        { return new Symbol(CalcParserSym.FLOAT, yyline + 1, yycolumn + 1, toFloat(yytext()));   }
"["            { return new Symbol(CalcParserSym.OSB, yyline + 1, yycolumn + 1 );  }
"]"            { return new Symbol(CalcParserSym.CSB, yyline + 1, yycolumn + 1 );  }
"{"            { return new Symbol(CalcParserSym.OB, yyline + 1, yycolumn + 1 );   }
"}"            { return new Symbol(CalcParserSym.CB, yyline + 1, yycolumn + 1 );   }
"?"            { return new Symbol(CalcParserSym.INTER, yyline + 1, yycolumn + 1 );}
"+"            { return new Symbol(CalcParserSym.PLUS, yyline + 1, yycolumn + 1 ); }
"-"            { return new Symbol(CalcParserSym.MINUS, yyline + 1, yycolumn + 1 );}
"="            { return new Symbol(CalcParserSym.ATTR, yyline + 1, yycolumn + 1 ); }
"<"            { return new Symbol(CalcParserSym.LT, yyline + 1, yycolumn + 1 ); }
"<="           { return new Symbol(CalcParserSym.LTE, yyline + 1, yycolumn + 1 ); }
"=="           { return new Symbol(CalcParserSym.EQ, yyline + 1, yycolumn + 1 ); }
";"            { return new Symbol(CalcParserSym.SEMI, yyline + 1, yycolumn + 1 ); }
":"            { return new Symbol(CalcParserSym.COLON, yyline + 1, yycolumn + 1 );}
","            { return new Symbol(CalcParserSym.COMA, yyline + 1, yycolumn + 1 ); }
"*"            { return new Symbol(CalcParserSym.TIMES, yyline + 1, yycolumn + 1); }
"/"            { return new Symbol(CalcParserSym.DIV, yyline + 1, yycolumn + 1); }
"("            { return new Symbol(CalcParserSym.LP, yyline + 1, yycolumn + 1 ); }
")"            { return new Symbol( CalcParserSym.RP, yyline + 1, yycolumn + 1); }
"#"            { return new Symbol( CalcParserSym.PRINT, yyline + 1, yycolumn + 1); }
"@"            { return new Symbol( CalcParserSym.RETURN, yyline + 1, yycolumn + 1); }
{white}        {/* While reading whites do nothing*/ }
[^]            {/* Matches any char form the input*/
                throw new Error("Illegal character <"+ yytext()+">"); }
}


