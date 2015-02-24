package org.acmsl.javacss.java8.parser;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

@RunWith(JUnit4.class)
public class MethodHelperTest {
    @Test public void can_count_the_number_of_methods()
        throws Exception {
        String input =
              "public class MyClass {\n"

            + "    public int method1(String value) { return 1; }\n"
            + "}\n";

        String method =
            "    public int method{0}(String value) { return {0}; }\n";

        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();
        Assert.assertNotNull(ast);
    }
}
