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
import org.acmsl.javacss.java8.parser.Java8Parser.CompilationUnitContext;
import org.acmsl.javacss.java8.parser.Java8Parser.ImportDeclarationContext;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.List;

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

        ImportAddOperation visitor = new ImportAddOperation(myType);

        visitor.visit(this.tree);
    }

    protected static class ImportAddOperation
        extends Java8BaseVisitor<CompilationUnitContext> {

        private final String importType;

        public ImportAddOperation(String newType) {
            importType = newType;
        }

        @Override
        public CompilationUnitContext visitCompilationUnit(@org.antlr.v4.runtime.misc.NotNull final CompilationUnitContext ctx)
        {
            ImportDeclarationContext newImport = new ImportDeclarationContext(ctx, ctx.invokingState);
            newImport.addChild(new CommonToken(Java8Parser.IMPORT, Java8Parser.tokenNames[Java8Parser.IMPLEMENTS]));
            newImport.addChild(new CommonToken(Java8Parser.Identifier, importType));
            ctx.addChild(newImport);
            return super.visitCompilationUnit(ctx);
        }
    }
}
