
MAIN_CLASS = Lang2Compiler
JC = javac
CP = -cp .:tools/java-cup-11b-runtime.jar

all: lexer parser compile

lexer: calc/parser/calc.flex
	java -jar tools/jflex.jar -nobak -d calc/parser calc/parser/calc.flex

parser: calc/parser/calc.cup
	java -jar tools/java-cup-11b.jar -destdir calc/parser/ -parser Lang2Parser -symbols Lang2ParserSym calc/parser/calc.cup

compile: lexer parser
	$(JC) $(CP) $(MAIN_CLASS).java calc/parser/*.java calc/nodes/*.java calc/nodes/command/*.java calc/nodes/decl/*.java calc/nodes/expr/*.java calc/nodes/types/*.java calc/nodes/environment/*.java calc/nodes/visitors/*.java

clean:
	rm -f calc/parser/CalcLexer.java
	rm -f calc/parser/Lang2Parser.java
	rm -f calc/parser/Lang2ParserSym.java
	find . -name "*.class" -delete
