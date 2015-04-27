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

package dsl.utility;

import dsl.ast.*;
import dsl.ast.ASTVisitor;
import static dsl.ast.ASTNodeFactory.*;

/**
 * Print out a simple representation of an AST.
 * @version Feb 14, 2015
 */
public class ASTDisplayer extends ASTVisitor<String>
{
	private final String indentString;
	private int indentLevel;
	private final StringBuilder sb;
	
	public ASTDisplayer()
	{
		sb = new StringBuilder();
		indentString = "  ";
		indentLevel = 0;
	}
	
	public String visit(CreatePlaylistNode node) 
	{ 
		sb.append("create " + node.playlistName);
		indentLevel++;
		visitChildren(node); 
		return sb.toString();
	}
	
	public String visit(QueryNode node) 
	{
		indent();
		sb.append(node.type + " " + node.getQueryStringNode().token.getText());
		
		return null;
	}
	
	
	public String visit(QueryListNode node) 
	{ 
		indent();
		sb.append(node.token.getText());
		indentLevel++;
		visitChildren(node);
		indentLevel--;
		return null;
	}
	
	public String visit(QueryStringNode node) 
	{ 
		indent();
		sb.append(node.shortString() + " " + node.getName());
		return null;
	}
	
	
	private void indent()
	{
		sb.append("\n");
		for (int i=0; i < indentLevel; i++) {
			sb.append(indentString);
		}
	}
}
