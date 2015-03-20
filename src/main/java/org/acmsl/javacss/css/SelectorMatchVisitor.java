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
 * Filename: SelectorMatchVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2015/03/20
 * Time: 10:35
 *
 */
package org.acmsl.javacss.css;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.javacss.java8.parser.Java8BaseVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2015/03/20 10:35
 */
@ThreadSafe
public class SelectorMatchVisitor
        extends Java8BaseVisitor<ParseTree>
    {
        boolean match = false;

        final ParseTree focusNode;
        final List<String> selectors;
        final Iterator<String> iterator;
        String currentSelector = null;

        Stack<String> consumedSelectors = new Stack<String>();

        public SelectorMatchVisitor(List<String> selectors, ParseTree focusNode) {
            this.selectors = selectors;
            this.iterator = selectors.iterator();
            if (this.iterator.hasNext()) {
                this.currentSelector = this.iterator.next();
            }
            this.focusNode = focusNode;
        }

        @Override
        public ParseTree visit(ParseTree node) {
            ParseTree result;

            if (matches(node, this.currentSelector)) {
                if (this.iterator.hasNext()) {
                    consumedSelectors.push(this.currentSelector);
                    this.currentSelector = this.iterator.next();
                    result = super.visit(node);
                } else if (focusNode.equals(node)) {
                    match = true;
                    result = null;
                } else {
                    result = null;
                }
            } else {
                result = super.visit(node);
            }

            return result;
        }

        protected boolean matches(final ParseTree node, final String currentSelector) {
            boolean result = false;

            if (currentSelector.startsWith(".")) {
                // class selector
                Object o = node.getPayload().getClass();
                result = node.getPayload().getClass().getSimpleName().equals(currentSelector.substring(1));
            } else if (currentSelector.startsWith("\"")) {
                result = node.getPayload().toString().equals(currentSelector.substring(1, currentSelector.lastIndexOf("\"")));
            }

            return result;
        }

        public boolean matchFound() {
            return this.match;
        }
}
