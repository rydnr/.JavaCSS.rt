package org.acmsl.javacss.css.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

@RunWith(JUnit4.class)
public class StringTemplateCSSParserTest
{
    /**
     * Builds an error manager to make the tests fail upon any error.
     * @return such {@link ErrorManager}.
     */
    protected ErrorManager buildErrorManager() {
        return
            new ErrorManager(
                new STErrorListener() {
                    @Override
                    public void compileTimeError(final STMessage msg) {
                        Assert.fail(msg.toString());
                    }

                    @Override
                    public void runTimeError(final STMessage msg) {
                        Assert.fail(msg.toString());
                    }

                    @Override
                    public void IOError(final STMessage msg) {
                        Assert.fail(msg.toString());
                    }

                    @Override
                    public void internalError(final STMessage msg) {
                        Assert.fail(msg.toString());
                    }
                });
    }

    @Test
    public void parses_a_simple_input() {
        String input =
            ".packageDeclaration #identifier::before {\n"
          + "    content: \"  \";\n"
          + "}\n";

        StringTemplateCSSLexer lexer = new StringTemplateCSSLexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        StringTemplateCSSParser parser = new StringTemplateCSSParser(tokens);
        ParseTree ast = parser.css();
        Assert.assertNotNull(ast);
    }

    @Test
    public void parses_another_simple_input() {
        String input =
              "  .packageDeclaration \";\"::after {\n"
            + "    content: \"\\n\\n\";\n"
            + "  }";

        StringTemplateCSSLexer lexer = new StringTemplateCSSLexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        StringTemplateCSSParser parser = new StringTemplateCSSParser(tokens);
        ParseTree ast = parser.css();
        Assert.assertNotNull(ast);
    }
}