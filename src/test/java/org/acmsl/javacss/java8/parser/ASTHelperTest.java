/*
                        JavaCSS

    Copyright (C) 2015-today  Jose San Leandro Armendariz
                              chous@acm-sl.org
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: ASTHelperTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2015/02/26
 * Time: 14:56
 *
 */
package org.acmsl.javacss.java8.parser;

/*
 * Importing JetBrains annotations.
 */
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.jetbrains.annotations.NotNull;

/*
 * Importing JUnit classes.
 */
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link ASTHelper}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2015/02/26 14:56
 */
@RunWith(JUnit4.class)
public class ASTHelperTest
{
    @Test
    public void add_new_AST_node()
        throws Exception
    {
        Java8Parser parser = buildParser();
        ParseTree tree = buildAST(parser);
        Assert.assertNotNull(tree);

        ASTHelper astHelper = new ASTHelper(tree);


        for (ParseTree node : XPath.findAll(tree, "//import", parser)) {

    }

    protected Java8Parser buildParser()
        throws Exception {
        String input =
            "public interface Resolver\n"
            + "    extends Serializable {\n\n"

            + "    public int resolve(String value);\n"
            + "}\n";

        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        return new Java8Parser(tokens);
    }

    protected ParseTree buildAST(Java8Parser parser)
        throws Exception {
        return parser.compilationUnit();
    }
}