package org.acmsl.javacss.java8.parser;

import org.acmsl.javacss.java8.parser.Java8Parser.MethodDeclaratorContext;
import org.acmsl.javacss.java8.parser.Java8Parser.MethodHeaderContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.xpath.XPath;

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

    public List<String> retrieveReturnTypesOfMethods()
    {
        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        return retrieveReturnTypesOfMethods(ast, parser);
    }

    public List<String> retrieveReturnTypesOfMethods(ParseTree tree, Java8Parser parser)
    {
        List<String> result = new ArrayList<>();

        for (ParseTree node : XPath.findAll(tree, "//methodHeader/result/*", parser)) {
            if (node instanceof TerminalNode) {
                result.add(((TerminalNode) node).getText());
            }
        }

        return result;
    }

    public List<String> retrieveReturnTypesOfMethods2(ParseTree node)
    {
        List<String> result = new ArrayList<>();

        ParseTreeWalker walker = new ParseTreeWalker();
        ReturnTypesOfMethodsListener listener = new ReturnTypesOfMethodsListener();
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
        public void exitMethodHeader(@NotNull final MethodHeaderContext ctx)
        {
            List<TerminalNode> tokens = ctx.getTokens(Java8Parser.RULE_result);

            ParseTree node = ctx.getChild(0);
            Java8Parser.ResultContext resultContext = (Java8Parser.ResultContext) node.getPayload();
            Java8Parser.UnannTypeContext c = (Java8Parser.UnannTypeContext) resultContext.getChild(0);
            TerminalNode s = (TerminalNode) c.getChild(0);
            returnTypes.add(ctx.getTokens(Java8Parser.RULE_result).get(0).getText());

            super.exitMethodHeader(ctx);
        }

        public List<String> getReturnTypesOfMethods() {
            return this.returnTypes;
        }
    }
}
