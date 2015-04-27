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

import java.util.*;

import org.antlr.v4.runtime.Token;

import dsl.lexparse.DSLVisitor;
import dsl.utility.DSLType;

/**
 * Base class for the Dijkstra AST nodes
 * @version Feb 9, 2015
 */
public abstract class ASTNode
{
	public enum ASTNodeType {
		CREATEPLAYLIST, QUERYLIST, QUERY, QUERYSTRING, FUNCTION
//		PROGRAM, DECL, ASSIGN, ARRAY, INPUT, OUTPUT, ITERATIVE, COMPOUND, ALTERNATIVE,
//		GUARD, ID, CONSTANT, UNARYEXPR, BINARYEXPR, PARAMETER, FUNCTION, PROCEDURE, RETURN, ARRAYACCESSOR, FUNCTIONCALL, PROCEDURECALL, 
	};
	
	
	
	public ASTNodeType nodeType;
	public ASTNode parent;
	public List<ASTNode> children;
	public Token token;
	public DSLType type;
	
	public ASTNode()
	{
		this(null);
	}
	
	public ASTNode(ASTNode parent) 
	{
		children = new ArrayList<ASTNode>();
		this.parent = parent;
		token = null;
		type = DSLType.UNDEFINED;
	}
	
	public <T> T accept(ASTVisitor<? extends T> visitor) { return visitor.visit(this); }
	
	/**
	 * @param i
	 * @return the ith child
	 * @throws ArrayOutOfBoundsException if i is invalid
	 */
	public ASTNode getChild(int i)
	{
		return children.get(i);
	}
	
	/**
	 * Add a child to this node. By default, this is placed at the rightmost child
	 * @param child
	 */
	public void addChild(ASTNode child)
	{
		children.add(child);
		child.parent = this;
	}
	
	/**
	 * Insert the child as the ith child of this node. Pushes all nodes to the right.
	 * @param i
	 * @param child
	 * @throws IndesOutOfBoundsException if i isout of bounds
	 */
	public void addChild(int i, ASTNode child)
	{
		children.add(i, child);
		child.parent = this;
	}
	
	/**
	 * Remove the ith child from this node and shift all of the other children to the left.
	 * @param i
	 * @return the removed node
	 * @throws IndesOutOfBoundsException if i is out of bounds
	 */
	public ASTNode removeChild(int i)
	{
		ASTNode child = children.remove(i);
		child.parent = null;
		return child;
	}
	
	/**
	 * @return the number of children
	 */
	public int getChildCount()
	{
		return children.size();
	}

	/**
	 * Print the complete tree rooted at this node.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(" + nodeType + nodeInfo() + ")";
	}
	
	/**
	 * @return common node information
	 */
	protected String nodeInfo()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(" type=" + type);
		builder.append(token == null ? "" : ", token=" + token.getText());
		builder.append(extraInfo());
		for (ASTNode n : children) {
			builder.append(" " + n);
		}
		return builder.toString();
	}
	
	/**
	 * This can be overwritten by the specific ASTNode subclasses.
	 * @return any extra information about the node
	 */
	protected String extraInfo()
	{
		return "";
	}
	
	/**
	 * This method is used for printing the bare minimum of just this node.
	 * @return a short string for a node
	 */
	public String shortString()
	{
		return nodeType.toString();
	}
	public DSLType getType() {
		return this.type;
		}
		
}


