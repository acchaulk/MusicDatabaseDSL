/*******************************************************************************
 * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/

package dsl.ast;
import static dsl.ast.ASTNodeFactory.*;

/**
 * The base Dijkstra AST visitor.
 * @version Feb 9, 2015
 */
public class ASTVisitor<T>
{
	public T visit(ASTNode node) { return visitChildren(node); }
	public T visit(CreatePlaylistNode node) { return visitChildren(node); }
	public T visit(QueryListNode node) { return visitChildren(node); }
	public T visit(QueryNode node) { return visitChildren(node); }
	public T visit(QueryStringNode node) { return null; }
		
	/**
	 * Visit all of the children without getting any results
	 * @param node the node whose children must be visited
	 */
	public T visitChildren(ASTNode node) 
	{
		for (ASTNode child : node.children) {
			child.accept(this);
		}
		return null;
	}
}
