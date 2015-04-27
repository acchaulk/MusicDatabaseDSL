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

package dsl.symbol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;

import dsl.ast.ASTNode;
import dsl.ast.ASTVisitor;
import dsl.ast.ASTNodeFactory.*;
import dsl.utility.DSLType;
import static dsl.utility.DSLType.*;
import static dsl.ast.ASTNode.ASTNodeType.*;

/**
 * Create the symbol table entries by visiting the AST that was created by
 * ASTCreator.
 * 
 * @version Feb 15, 2015
 */
public class SymbolCreator extends ASTVisitor<DSLType> {
	private final SymbolTableManager stm;

	/**
	 * Default constructor.
	 */
	public SymbolCreator() {
		stm = SymbolTableManager.getInstance();
	}

	public DSLType visit(QueryNode queryNode) {
		QueryStringNode q = (QueryStringNode) queryNode.getQueryStringNode();
		q.type = queryNode.type;
		q.symbol = stm.addIfNew(q.token.getText(), queryNode.type);

		return queryNode.type;
	}

//	/**
//	 * This ID is in an expression. If there is no symbol, then it is an error
//	 * because it should have been defined before this.
//	 * 
//	 * @see dijkstra.ast.ASTVisitor#visit(dijkstra.ast.ASTNodeFactory.IDNode)
//	 */
//	public DSLType visit(QueryStringNode queryStringNode) {
//		Symbol s = stm.getSymbol(queryStringNode.getName());
//		if (s == null) {
//			throw new DSLSymbolException("Cannot use symbol before it is declared "+ queryStringNode.getName());
//		}
//		queryStringNode.symbol = s;
//		queryStringNode.type = s.getType();
//		return queryStringNode.type;
//	}

}
