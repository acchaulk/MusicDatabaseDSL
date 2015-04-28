/*******************************************************************************
f * Copyright (c) 2015 Gary F. Pollice
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Used in CS4533/CS544 at Worcester Polytechnic Institute
 *******************************************************************************/

package dsl.ast;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;

import dsl.lexparse.*;
import dsl.lexparse.DSLParser.DslTextContext;
import dsl.utility.DSLType;
import static dsl.ast.ASTNodeFactory.*;
import static dsl.lexparse.DSLParser.*;

/**
 * Description
 * 
 * @version Feb 10, 2015
 */
public class ASTCreator extends DSLBaseVisitor<ASTNode> {
	/**
	 * Return the result of visiting the program node.
	 * 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitDijkstraText(dijkstra.lexparse.DSLParser.DijkstraTextContext)
	 */
	public ASTNode visitDslText(DSLParser.DslTextContext ctx) {
		return ctx.createPlaylist().accept(this);
	}

	/**
	 * Return the program node after visiting and linking the appropriate
	 * children nodes.
	 * 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitProgram(dijkstra.lexparse.DSLParser.ProgramContext)
	 */
	public ASTNode visitCreatePlaylist(DSLParser.CreatePlaylistContext ctx) {
		String playlistName = ctx.ID().getText();
		final CreatePlaylistNode playlist = CreatePlaylistNode.makeCreatePlaylistNode(playlistName);
		for (int i = 2; i < ctx.getChildCount(); i++) {
			playlist.addChild(ctx.getChild(i).accept(this));
		}
		return playlist;
	}

	/**
	 * Visit a parse tree produced by {@link DSLParser#declaration}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the declaration linked up to the parent
	 */
	public ASTNode visitQuery(DSLParser.QueryContext ctx) {
		TerminalNode tn = null;
		TerminalNode tnFunction = null;
		
		if(ctx.queryString().STRING() != null) {
			tn = ctx.queryString().STRING();
		} 
		else if(ctx.queryString().STRING_NONUM() != null) {
			tn = ctx.queryString().STRING_NONUM();
		}
		else if (ctx.queryString().YEAR_STRING() != null) {
			tn = ctx.queryString().YEAR_STRING();
		}
		if(ctx.function().SAMEDECADE() !=null){
			tnFunction = ctx.function().SAMEDECADE();
		}
		else if(ctx.function().SAMEGENRE() != null){
			tnFunction = ctx.function().SAMEGENRE();
		}
		else if(ctx.function().SIMILARTO() != null){
			tnFunction = ctx.function().SIMILARTO();
		}
		
		Token t = tn.getSymbol();
		Token t2 = tnFunction.getSymbol();
		
		QueryStringNode queryString = QueryStringNode.makeQueryNode(t); 
		FunctionNode f = FunctionNode.makeFunctioNode(t2);
		final DSLType type = DSLType.getType(ctx.type().getText());
		final QueryNode query = QueryNode.makeQueryNode(type,f, queryString);
		return query;
	}

	/**
	 * There are different types of expressions. For the AST, there are three
	 * types, depending upon the number of operators.
	 * 
	 * @see dijkstra.lexparse.DijkstraBaseVisitor#visitExpression(dijkstra.lexparse.DSLParser.ExpressionContext)
	 */
	public ASTNode visitQueryList(DSLParser.QueryListContext ctx) {
		ASTNode result = null;
		TerminalNode tn;
		Token t;
		ASTNode expr0 = null, expr1 = null;
		
		

		switch (ctx.getChildCount()) {
		case 1: // Single Query
//			tn = (TerminalNode) ctx.getChild(0);
//			t = tn.getSymbol();
//			QueryStringNode queryString = QueryStringNode.makeQueryNode(t);//ctx.query().queryString().getSymbol()); //TODO fix getSymbol
//			final DSLType type = DSLType.getType(ctx.query().type().getText());
//			result = QueryNode.makeQueryNode(type, null, queryString);
			
//			result = QueryListNode.makeQueryListNode(ctx.queryList(0).accept(this));
			ASTNode q =  visitQuery(ctx.query());
			result = QueryListNode.makeQueryListNode(q);
//			result = visitQuery(ctx.query());
			break;
		case 3: // QueryList (& |) QueryList
			if (ctx.queryList().size() == 1) {
				result = ctx.queryList(0).accept(this);
			} else {
				tn = (TerminalNode) ctx.getChild(1); // the operator
				result = QueryListNode.makeQueryListNode(tn.getSymbol(), ctx.queryList(0).accept(this), 
						ctx.queryList(1).accept(this));
				break;
			}
		}
		return result;
	}

	/**
	 * Return null for a type rule. The type can be found by the parent
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	public ASTNode visitType(DSLParser.TypeContext ctx) {
		return null;
	}
}
