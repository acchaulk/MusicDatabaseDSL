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

package dsl.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.boon.json.serializers.impl.JsonSimpleSerializerImpl;

import json.templates.*;
import dsl.JSONFetcher;
import dsl.JsonTutorial;
import dsl.ast.*;
import dsl.ast.ASTNode.ASTNodeType;
import dsl.ast.ASTNodeFactory.*;
import dsl.lexparse.DSLParser;
import dsl.utility.*;
import static dsl.utility.DSLType.*;
import static dsl.ast.ASTNode.ASTNodeType.*;

/**
 * Description
 * 
 * @version Feb 21, 2015
 */
public class DSLCodeGenerator extends ASTVisitor<String> {


	// 		create playlist artist.similarTo("Nirvana") & artist.similarTo("Foo Fighters")
	// 		create playlist artist.similarTo("Nirvana") & artist.similarTo("Foo Fighters") | artist.similarTo("Megadeth")
	// 		create playlist artist.similarTo("Nirvana") & artist.similarTo("Foo Fighters") | artist.similarTo("Pearl Jam")
	//		create p artist.similarTo("Nirvana") & artist.sameGenre("Rock") & artist.sameDecade("1990")

	public Stack<ResponseHolder> responses;
	public List<String> excluded;

	public DSLCodeGenerator() {
		responses = new Stack<ResponseHolder>();
		excluded = new ArrayList<String>();

	}

	public String visit(CreatePlaylistNode node) {
//		System.out.println("Got to CreatePlaylistNode");
		visitChildren(node);
		
		if(responses.size() == 1) {
			ResponseHolder r = responses.peek();
			List<String> artistStrings = JSONFetcher.artistsToString(r.response.artists);
			System.out.println("--Here are some suggestions for you--");
			for(String a : excluded) {
				artistStrings.remove(a);
			}
			for(String a : artistStrings) {
				System.out.println(a.toString());
			}
		}
		
		return null;
	}

	public String visit(QueryListNode node) {
//		System.out.println("Got to QueryListNode");

		if(node.children.size() == 2) {
			node.getExpr1().accept(this);
			node.getExpr2().accept(this);
			
			ResponseHolder response1 = responses.pop();
			ResponseHolder response2 = responses.pop();

			if(node.getOp() == DSLParser.AND) {
				if(node.getExpr1().type.toString().equals("ARTIST")) {
					List<String> artistStrings = JSONFetcher.andListOp(
							JSONFetcher.artistsToString(response1.response.artists)
							, JSONFetcher.artistsToString(response2.response.artists));

					response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
					responses.push(response1);
				}
			}
			else if (node.getOp() == DSLParser.OR) {
				if(node.getExpr1().type.toString().equals("ARTIST")) {
					List<String> artistStrings = JSONFetcher.orListOp(JSONFetcher.artistsToString(response1.response.artists)
							, JSONFetcher.artistsToString(response2.response.artists));

					response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
					responses.push(response1);
				}
			}
		}

		else { // single Query
			node.getExpr1().accept(this);
			ResponseHolder response1 = responses.pop();
			List<String> artistStrings = JSONFetcher.artistsToString(response1.response.artists);
			responses.push(response1);

		}
		return null;
	}

	public String visit(QueryNode node) {
//		System.out.println("Got to QueryNode");

		String queryFunction = node.getFunction().token.getText(); //similarTo, sameGenre, sameDecade
		String queryType = node.getType().toString();
		String queryString = node.getQueryStringNode().token.getText();
		excluded.add(queryString.replaceAll("^\"|\"$", ""));
//		System.out.println(queryString);
		switch(queryFunction) {
		case "similarTo": 
			responses.push(JSONFetcher.similarTo(queryString, queryType));
			break;
		case "sameGenre": 
			responses.push(JSONFetcher.sameGenre(queryString, queryType));
			break;
		case "sameDecade":
			responses.push(JSONFetcher.sameDecade(queryString, queryType));
			break;
		default: break;
		}
		return null;
	}

	public String visit(QueryStringNode node) {
//		System.out.println("Got to QueryStringNode");
		return null;
	}




}
