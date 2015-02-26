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
 * Filename: ASTHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2015/02/26
 * Time: 15:20
 *
 */
package org.acmsl.javacss.java8.parser;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.javacss.java8.parser.Java8Parser.ImportDeclarationContext;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2015/02/26 15:20
 */
@ThreadSafe
public class ASTHelper
{
    private final ParseTree tree;

    public ASTHelper(ParseTree ast) {
        this.tree = ast;
    }

    public void addImport(final String myType) {

    }

    protected static class ImportVisitor
        extends Java8BaseVisitor {
        @Override
        public Object visitImportDeclaration(@org.antlr.v4.runtime.misc.NotNull final ImportDeclarationContext ctx)
        {
            TerminalNode new TerminalNode()
            {
                @Override
                public Token getSymbol()
                {
                    return null;
                }

                @Override
                public ParseTree getParent()
                {
                    return null;
                }

                @Override
                public ParseTree getChild(final int i)
                {
                    return null;
                }

                @Override
                public <T> T accept(final ParseTreeVisitor<? extends T> parseTreeVisitor)
                {
                    return null;
                }

                @Override
                public String getText()
                {
                    return null;
                }

                @Override
                public String toStringTree(final Parser parser)
                {
                    return null;
                }

                @Override
                public Interval getSourceInterval()
                {
                    return null;
                }

                @Override
                public Object getPayload()
                {
                    return null;
                }

                @Override
                public int getChildCount()
                {
                    return 0;
                }

                @Override
                public String toStringTree()
                {
                    return null;
                }
            })
            return super.visitImportDeclaration(ctx);
        }
    }
}
