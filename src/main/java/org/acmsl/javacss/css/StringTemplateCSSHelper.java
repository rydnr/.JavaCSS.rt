/*
                        devdel

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
 * Filename: StringTemplateCSSHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2015/03/02
 * Time: 17:13
 *
 */
package org.acmsl.javacss.css;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.javacss.css.parser.StringTemplateCSSBaseVisitor;
import org.acmsl.javacss.css.parser.StringTemplateCSSLexer;
import org.acmsl.javacss.css.parser.StringTemplateCSSParser;
import org.acmsl.javacss.css.parser.StringTemplateCSSParser.PropertyContext;
import org.acmsl.javacss.css.parser.StringTemplateCSSVisitor;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2015/03/02 17:13
 */
@ThreadSafe
public class StringTemplateCSSHelper
{
    private final String input;
    private List<List<String>> selectors;
    private Map<List<String>, Map<String, String>> properties;

    public StringTemplateCSSHelper(final String input)
    {
        this.input = input;
    }

    public List<List<String>> getSelectors()
    {
        if (this.selectors == null)
        {
            initialize(this.input);
        }
        
        return this.selectors;
    }

    protected void initialize(String input)
    {
        StringTemplateCSSLexer lexer = new StringTemplateCSSLexer(new ANTLRInputStream(input));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        StringTemplateCSSParser parser = new StringTemplateCSSParser(tokens);

        ParseTree tree = parser.css();

        Collection<ParseTree> selectorCombinations = XPath.findAll(tree, "//selectorCombination", parser);

        this.selectors = new ArrayList<List<String>>(selectorCombinations.size());
        this.properties = new HashMap<List<String>, Map<String, String>>();

        for (ParseTree selectorCombination : selectorCombinations)
        {
            List<String> currentSelectors = new ArrayList<String>(selectorCombination.getChildCount());
            this.selectors.add(currentSelectors);

            for (int index = 0; index < selectorCombination.getChildCount(); index++)
            {
                String text = selectorCombination.getChild(index).getText();
                currentSelectors.add(text);
            }
            Map<String, String> block = retrieveProperties(selectorCombination, parser);

            this.properties.put(currentSelectors, block);
        }
    }

    protected Map<String, String> retrieveProperties(ParseTree selectorEntry, StringTemplateCSSParser parser)
    {
        Map<String, String> result;

        Collection<ParseTree> properties = findPropertyNodes(selectorEntry);

        result = new HashMap<String, String>(properties.size());

        for (ParseTree property : properties)
        {
            String key = property.getChild(0).getText();
            String value = property.getChild(2).getText();
            result.put(key, value);
        }

        return result;
    }

    protected Collection<ParseTree> findPropertyNodes(final ParseTree selectorEntry)
    {
        ParseTree parent = selectorEntry.getParent();
        PropertyVisitor visitor = new PropertyVisitor(selectorEntry.getParent());
        parent.accept(visitor);

        return visitor.properties;
    }

    public Map<String, String> getProperties(List<String> selector)
    {
        if (this.properties == null)
        {
            initialize(this.input);
        }

        return this.properties.get(selector);
    }

    public String retrieveMatchingSelectors(ParseTree semiColon)
    {
        List<String> selectors
    }

    protected static class PropertyVisitor
        extends StringTemplateCSSBaseVisitor<ParseTree>
    {
        final List<ParseTree> properties = new ArrayList<ParseTree>();
        final ParseTree parent;

        public PropertyVisitor(final ParseTree parent)
        {
            this.parent = parent;
        }

        @Override
        public ParseTree visitProperty(@NotNull final PropertyContext ctx)
        {
            if (ctx.getParent() == this.parent)
            {
                this.properties.add(ctx);
            }
            return super.visitProperty(ctx);
        }
    }
}
