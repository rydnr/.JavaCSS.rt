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
 * Filename: StringTemplateCSSHelperTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for StringTemplateCSSHelper.
 *
 * Date: 2015/03/02
 * Time: 16:52
 *
 */
package org.acmsl.javacss.css;

import org.acmsl.javacss.java8.parser.Java8Lexer;
import org.acmsl.javacss.java8.parser.Java8Parser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link StringTemplateCSSHelper}.
 * @author <a href="mailto:jose@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2015/03/02 16:52
 */
@RunWith(JUnit4.class)
public class StringTemplateCSSHelperTest
{
    @Test
    public void retrieves_selectors_for_a_simple_input()
    {
        String input =
            ".packageDeclaration #identifier::before {\n"
            + "    content: \"  \";\n"
            + "}\n";

        StringTemplateCSSHelper helper = new StringTemplateCSSHelper(input);

        List<String> selectors = helper.getSelectors();

        Assert.assertNotNull(selectors);

        Assert.assertEquals(1, selectors.size());

        Assert.assertEquals(".packageDeclaration#identifier::before", selectors.get(0));
    }

    protected void multipleBlockSelectorTests(int count)
    {
        StringBuilder input = new StringBuilder();

        for (int index = 0; index < count; index++)
        {
            input.append(".packageDeclaration #identifier");
            input.append(index);
            input.append("::before {\n    content: \"  \";\n}\n\n");
        }

        StringTemplateCSSHelper helper = new StringTemplateCSSHelper(input.toString());

        List<String> selectors = helper.getSelectors();

        Assert.assertNotNull(selectors);

        Assert.assertEquals(count, selectors.size());

        for (int index = 0; index < count; index++)
        {
            Assert.assertEquals(".packageDeclaration#identifier" + index + "::before", selectors.get(index));
        }
    }

    @Test
    public void retrieves_selectors_for_an_input_with_several_blocks()
    {
        multipleBlockSelectorTests((int) (Math.random() * 10));
    }

    protected void multipleBlockPropertyTests(int count)
    {
        StringBuilder input = new StringBuilder();

        for (int index = 0; index < count; index++)
        {
            input.append(".packageDeclaration #identifier");
            input.append(index);
            input.append("::before {\n");
            input.append("    content: \"");
            input.append(index);
            input.append("\";\n");
            input.append("}\n");
        }

        StringTemplateCSSHelper helper = new StringTemplateCSSHelper(input.toString());

        List<String> selectors = helper.getSelectors();

        Assert.assertNotNull(selectors);

        Assert.assertEquals(count, selectors.size());

        for (int index = 0; index < count; index++)
        {
            Map<String, String> properties = helper.getProperties(selectors.get(index));

            Assert.assertNotNull(properties);

            Assert.assertEquals(1, properties.size());

            Assert.assertTrue(properties.containsKey("content"));
            Assert.assertEquals("\"" + index + "\"", properties.get("content"));
        }
    }

    @Test
    public void retrieves_properties_for_a_simple_input()
    {
        multipleBlockPropertyTests(1);
    }

    @Test
    public void retrieves_properties_for_an_input_with_multiple_blocks()
    {
        multipleBlockPropertyTests((int) (Math.random() * 10));
    }

    @Test
    public void finds_the_matching_selector()
    {
        String javaInput = "package com.foo.bar;";

        String cssInput =
              ".packageDeclaration \";\"::before {\n"
            + "   content: \" \";\n"
            + "}\n";

        StringTemplateCSSHelper helper = new StringTemplateCSSHelper(cssInput);

        Java8Lexer lexer = new Java8Lexer(new ANTLRInputStream(javaInput));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree ast = parser.compilationUnit();

        Collection<ParseTree> matches = XPath.findAll(ast, "//';'", parser);

        ParseTree semiColon = matches.toArray(0);
    }
}

