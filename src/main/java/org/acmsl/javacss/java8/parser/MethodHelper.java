package org.acmsl.javacss.java8.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MethodHelper {
    private final String input;

    public MethodHelper(String input) {
        this.input = input;
    }

    public int countMethods() {
        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        ast.getChild()
        return 0;
    }
}
