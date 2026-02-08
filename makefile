
all: CalcCompiler.class

CalcCompiler.class:  calc/parser/CalcLexer.java calc/parser/CalcParser.java
	javac  -cp .:tools/java-cup-11b-runtime.jar:tools/ST-4.3.4.jar CalcCompiler.java


calc/parser/CalcParser.java:
	java -jar tools/java-cup-11b.jar -destdir calc/parser/ calc/parser/calc.cup

calc/parser/CalcLexer.java:
	java -jar  tools/jflex.jar -nobak   -d calc/parser calc/parser/calc.flex

cleanClasses:
	find  -name "*.class" -delete

clean: cleanClasses cleanParser


cleanParser:
	rm calc/parser/CalcLexer.java
	rm calc/parser/CalcParser.java
	rm calc/parser/CalcParserSym.java

cleanSamples:
	find -name "*.dot" -delete
	find -name "*.jpeg" -delete
