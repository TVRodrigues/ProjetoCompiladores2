
MAIN_CLASS = Lang2Compiler
JC = javac
CP = -cp .:tools/java-cup-11b-runtime.jar:tools/ST-4.3.4.jar

all: lexer parser compile

# 1. Gera o Lexer
lexer: calc/parser/calc.flex
	java -jar tools/jflex.jar -nobak -d calc/parser calc/parser/calc.flex

# 2. Gera o Parser (Lang2Parser.java e Lang2ParserSym.java)
# A flag -parser define o nome da classe gerada
parser: calc/parser/calc.cup
	java -jar tools/java-cup-11b.jar -destdir calc/parser/ -parser Lang2Parser -symbols Lang2ParserSym calc/parser/calc.cup

# 3. Compila tudo
compile: lexer parser
	$(JC) $(CP) $(MAIN_CLASS).java calc/parser/*.java calc/nodes/*.java

# Limpeza
clean:
	rm -f calc/parser/CalcLexer.java
	rm -f calc/parser/Lang2Parser.java
	rm -f calc/parser/Lang2ParserSym.java
	find . -name "*.class" -delete
