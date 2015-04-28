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

package dsl.semantic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import dsl.ast.*;
import dsl.ast.ASTNodeFactory.*;
import dsl.lexparse.DSLParser;
import dsl.symbol.*;
import dsl.utility.DSLType;
import static dsl.utility.DSLType.*;
import static dsl.ast.ASTNode.ASTNodeType.*;

/**
 * Description
 * 
 * @version Feb 17, 2015
 */
public class TypeChecker extends ASTVisitor<DSLType> {
	private final Stack<DSLType> typeNeeded;
	private boolean continueChecking; // true if something changed
	private SymbolTableManager stm = SymbolTableManager.getInstance();

	private boolean debug = false;

	public TypeChecker() {
		typeNeeded = new Stack<DSLType>();
		continueChecking = false;
	}

	public DSLType visit(CreatePlaylistNode create) {
		continueChecking = false;
		visitChildren(create); // return value doesn't matter
		if (!checkAgain()) {
			validateSymbols();
		}
		return null;
	}

	public DSLType visit(QueryNode query) {
		//		System.out.println("Text = " + query.getFunction().token.getText());
		//		System.out.println("Text = " + query.getFunction().token.getText());
		//		System.out.println("Query String = " + query.getQueryStringNode());
		//		System.out.println("To String String =" + query.getQueryStringNode().token.toString());

		if(query.getType().toString().equals("GENRE")) {
			if(!query.getFunction().token.getText().toLowerCase().equals("similarto")) {
				throw new DSLSemanticException("Queries for Genre can only be for similarTo");
			}
		}


		if(query.getFunction().token.getText().toLowerCase().equals("samegenre")) {
			if(query.getQueryStringNode().token.getText().toLowerCase().matches(".*\\d+.*")) {
				throw new DSLSemanticException("Cannot have a genre with digit(s)");
			} 
		}
		return query.getType();
	}



	public DSLType visit(QueryListNode queryList) {
		//		DSLType expected = typeNeeded.peek(); //artist, song, album

		//		if (queryList.type == UNDEFINED && expected != queryList.type) {
		//			queryList.type = expected;
		//		}


		//		typeNeeded.push(need);
		if(queryList.children.size() == 2) {

			DSLType expr1Type = queryList.getExpr1().accept(this);
			DSLType expr2Type = queryList.getExpr2().accept(this);
			//		typeNeeded.pop();


			if(expr1Type == UNDEFINED || expr2Type == UNDEFINED) {
				continueChecking = true;
			}
			if(expr1Type == expr2Type) {
				queryList.type = expr1Type;
			}

			else if(expr1Type != expr2Type) {
				throw new DSLSemanticException("All queries must have the same type (artist, song, album)");
			}
		}
		
		else if( queryList.children.size() == 1) {
			DSLType expr1Type = queryList.getExpr1().accept(this);
			queryList.type = expr1Type;
			
		}
		return queryList.type;
	}

	/**
	 * @return the changed field's value
	 */
	public boolean checkAgain() {
		return continueChecking;
	}

	/**
	 * Make sure that there are no symbols that are undefined.
	 */
	private void validateSymbols() {
		for (SymbolTable st : stm.getTables()) {
			for (Symbol symbol : st.getSymbols()) {
				if (symbol.getType() == UNDEFINED) {
					throw new DSLSemanticException("Symbol '"
							+ symbol.getId() + "' is untyped");
				}
			}
		}
	}

	/**
	 * Print out a debugging message. If you're not debugging, just set the
	 * debugging variable.
	 * 
	 * @param msg
	 */
	private void debug(String msg) {
		if (debug) {
			System.out.println("DEBUG: " + msg);
		}
	}
}




//	@Override
//	public DSLType visitQuery(DSLParser.QueryContext ctx){
//		if(ctx.function().SAMEGENRE() != null) {
//			if(ctx.STRING() != null) {
//				throw new DSLSemanticException("Cannot have a genre with digit(s)");
//			}
//		}
//		System.out.println("Node type " + ctx.type().getText());
//		return DSLType.getType(ctx.type().getText());
//	}
//	
////
//	@Override
//	public DSLType visitQueryList(DSLParser.QueryListContext ctx) {
//		DSLType type = null;
//		for( QueryContext q : ctx.qu {
//			if(type == null) {
//				type = visitQuery(q);
//			}
////			else if(type != q.)
////				type = q.type
////		     else if(type != q.type )
////		    	 throw new Exception("All queries must have the same type (artist, song, album)");
//		}
//		return null;
//	}
//}
//
