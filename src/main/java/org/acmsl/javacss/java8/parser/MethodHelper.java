package org.acmsl.javacss.java8.parser;

import org.acmsl.javacss.java8.parser.Java8Parser.MethodDeclaratorContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

public class MethodHelper {
    private final String input;

    public MethodHelper(String input) {
        this.input = input;
    }

    public int countMethods() {
        int result;

        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        result = countMethods(ast);

        return result;
    }

    public int countMethods(ParseTree node) {
        ParseTreeWalker walker = new ParseTreeWalker();
        MethodCountListener listener = new MethodCountListener();
        walker.walk(listener, node);

        return listener.getMethodCount();
    }

    public List<String> retrieveReturnTypesOfMethods(String input)
    {
        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        return retrieveReturnTypesOfMethods(ast);
    }

    public List<String> retrieveReturnTypesOfMethods(ParseTree node)
    {
        List<String> result = new ArrayList<>();

        ParseTreeWalker walker = new ParseTreeWalker();
        ReturnTypesOfMethodsistener listener = new ReturnTypesOfMethodsListener();
        walker.walk(listener, node);

        return listener.getReturnTypesOfMethods();
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

    protected static class ReturnTypesOfMethodsListener
        extends Java8BaseListener {

        private final List<String> returnTypes = new ArrayList<String>();

        @Override
        public void exitMethodDeclarator(@NotNull final MethodDeclaratorContext ctx) {
            returnTypes.add();
            super.exitMethodDeclarator(ctx);
        }

        public int getMethodCount() {
            return this.methodCount;
        }
    }
}
