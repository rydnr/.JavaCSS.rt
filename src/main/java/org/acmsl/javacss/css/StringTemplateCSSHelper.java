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
import org.acmsl.javacss.css.parser.StringTemplateCSSLexer;
import org.acmsl.javacss.css.parser.StringTemplateCSSParser;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
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
    private List<String> selectors;
    private Map<String, Map<String, String>> properties;

    public StringTemplateCSSHelper(final String input)
    {
        this.input = input;
    }

    public List<String> getSelectors()
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

        Collection<ParseTree> selectorEntries = XPath.findAll(tree, "//selectorCombination", parser);

        this.selectors = new ArrayList<String>(selectorEntries.size());
        this.properties = new HashMap<String, Map<String, String>>();

        for (ParseTree selectorEntry : selectorEntries)
        {
            String text = selectorEntry.getText();
            this.selectors.add(text);
            Map<String, String> block = retrieveProperties(selectorEntry.getParent(), parser);

            this.properties.put(text, block);
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
        Collection<ParseTree> result;

        ParseTree parent = selectorEntry.getParent();
        parent.accept(
        return result;
    }

    public Map<String, String> getProperties(String selector)
    {
        if (this.properties == null)
        {
            initialize(this.input);
        }

        return this.properties.get(selector);
    }
}
