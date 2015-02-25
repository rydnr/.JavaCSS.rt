package org.acmsl.javacss.java8.parser;

import org.acmsl.javacss.java8.parser.Java8Parser.MethodDeclaratorContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class MethodHelper {
    private final String input;

    public MethodHelper(String input) {
        this.input = input;
    }

    public int countMethods() {
        int result = 0;

        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        result = countMethods(ast);

        return result;
    }

    public int countMethods(ParseTree node) {
        int result = 0;

        ParseTreeWalker walker = new ParseTreeWalker();
        MethodCountListener listener = new MethodCountListener();
        walker.walk(listener, );


        return result;
    }

    public boolean isMethod(ParseTree node) {
        return node.getPayload() instanceof Java8Parser.MethodDeclarationContext;
    }

    protected static class MethodCountListener
        extends Java8BaseListener {

        private int methodCount = 0;

        @Override
        public void exitMethodDeclarator(@NotNull final MethodDeclaratorContext ctx) {
            methodCount++;
            super.exitMethodDeclarator(ctx);
        }

        public int getMethodCount() {
            return this.methodCount;
        }
    }
}
