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

import java.util.List;
import java.util.Stack;

import org.boon.json.serializers.impl.JsonSimpleSerializerImpl;

import json.templates.*;
import dsl.JSONFetcher;
import dsl.JsonTutorial;
import dsl.ast.*;
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
	public String url = "http://developer.echonest.com/api/v4/artist/similar?api_key=DGRSTO8KKQIAWYCPY&name=Nirvana";

	public Stack<ResponseHolder> responses;

	public DSLCodeGenerator() {
		responses = new Stack<ResponseHolder>();

	}

	public String visit(CreatePlaylistNode node) {
		System.out.println("Got to CreatePlaylistNode");
		visitChildren(node);
		return null;
	}


	public String visit(QueryListNode node) {
		System.out.println("Got to QueryListNode");
		node.getExpr1().accept(this);
		node.getExpr2().accept(this);

//		QueryNode query1 = (QueryNode) node.getExpr1();
//		QueryNode query2 = (QueryNode) node.getExpr2();

		ResponseHolder response1 = responses.pop();
		ResponseHolder response2 = responses.pop();

		if(node.getOp() == DSLParser.AND) {
			if(node.getExpr1().type.toString().equals("ARTIST")) {

				System.out.println(response1.response.artists.toString());
				List<String> artistStrings = JSONFetcher.andListOp(JSONFetcher.artistsToString(response1.response.artists)
						, JSONFetcher.artistsToString(response2.response.artists));
				
				
				response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
				responses.push(response1);
				
				
				for(String a : artistStrings) {
					System.out.println("ARTIST: " + a.toString());
				}
			}
		}
		else if (node.getOp() == DSLParser.OR) {
			if(node.getExpr1().type.toString().equals("ARTIST")) {

				System.out.println(response1.response.artists.toString());
				List<String> artistStrings = JSONFetcher.orListOp(JSONFetcher.artistsToString(response1.response.artists)
						, JSONFetcher.artistsToString(response2.response.artists));
				for(String a : artistStrings) {
					System.out.println("ARTIST: " + a.toString());
				}
				
				response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
				responses.push(response1);
			}
		}

		return null;
	}

	public String visit(QueryNode node) {
		//		System.out.println("Got to QueryNode");
		//		System.out.println("Query String: " + node.getQueryStringNode().token.getText());
		//		System.out.println("Query Function: " + node.getFunction().token.getText());
		//		System.out.println("Query Type: " +  node.getType().toString());


		String queryFunction = node.getFunction().token.getText(); //similarTo, sameGenre, sameDecade
		String queryType = node.getType().toString();
		String queryString = node.getQueryStringNode().token.getText();



		switch(queryFunction) {
		case "similarTo": 
			responses.push(JSONFetcher.similarTo(queryString, queryType));
			break;
		case "sameGenre": 
			break;
		case "sameDecade":
			break;
		default: break;
		}




		return null;
	}

	public String visit(QueryStringNode node) {
		System.out.println("Got to QueryStringNode");
		return null;
	}




}
