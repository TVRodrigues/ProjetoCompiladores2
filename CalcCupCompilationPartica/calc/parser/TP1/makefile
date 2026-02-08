all: LangCompiler.class

LangCompiler.class:  LangLexer.java
	javac -cp . TK.java Token.java LangLexer.java LangCompiler.java

LangLexer.java: Lang.flex
	java -jar jflex.jar -nobak Lang.flex

cleanClasses:
	rm -f *.class

clean: cleanClasses
	rm -f LangLexer.java

cleanSamples:
	rm -f *.dot *.jpeg