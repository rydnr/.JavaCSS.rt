package org.acmsl.javacss.css;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;

@RunWith(JUnit4.class)
public class StringTemplateCSSParserTest
{
    @Test
    public void parses_a_simple_input() {
        String input =
            ".packageDeclaration #identifier::before {\n"
          + "    content: \"  \";\n
          + "}\n";

        StringTemplateCSSLexer lexer = new StringTemplateCSSLexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        StringTemplateCSSParser parser = new StringTemplateCSSParser(tokens);
        ParseTree ast = parser.css();
        Assert.assertNotNull(ast);
    }
}