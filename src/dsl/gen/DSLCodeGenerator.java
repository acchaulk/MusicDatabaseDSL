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
import json.templates.albums.Data;
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
	//		create p artist.similarTo("britney spears") & artist.sameGenre("Pop") & artist.sameDecade("1990")
	//		create p genre.similarTo("metal")
	// 		create p album.similarTo("Nevermind") | album.similarTo("Ten")

	public Stack<ResponseHolder> responses;
	public List<String> excluded;
	public String type;

	public DSLCodeGenerator() {
		responses = new Stack<ResponseHolder>();
		excluded = new ArrayList<String>();

	}

	public String visit(CreatePlaylistNode node) {
		//		System.out.println("Got to CreatePlaylistNode");
		visitChildren(node);
		if(responses.size() == 1) {
			ResponseHolder r = responses.peek();

			if(type.equals("ARTIST")) {
				List<String> artistStrings = JSONFetcher.artistsToString(r.response.artists);
				System.out.println("--Here are some suggested artists for you--");
				for(String a : excluded) {
					artistStrings.remove(a);
				}
				for(String a : artistStrings) {
					System.out.println(a.toString());
				}
			} 

			else if(type.equals("GENRE")) {
				List<String> genreStrings = JSONFetcher.genresToString(r.response.genres);
				System.out.println("--Here are some suggested genres for you--");
				for(String a : excluded) {
					genreStrings.remove(a);
				}
				for(String a : genreStrings) {
					System.out.println(a.toString());
				}
			}
			
			else if(type.equals("ALBUM")) {
				System.out.println("--Here are some suggested albums for you--");
				for(Data d : r.data) {
					if(!excluded.contains(d.title)) {
						System.out.println("Album Name: " + d.title + ", Artist Name: " + d.performer_name);
					}
				}
			}


		}

		return null;
	}

	public String visit(QueryListNode node) {
		//		System.out.println("Got to QueryListNode");

		type = node.getExpr1().type.toString();
		if(node.children.size() == 2) {
			node.getExpr1().accept(this);
			node.getExpr2().accept(this);
			ResponseHolder response1 = responses.pop();
			ResponseHolder response2 = responses.pop();
			String type = node.getExpr1().type.toString();//.equals("GENRE")

			/* Handle AND op */
			if(node.getOp() == DSLParser.AND) {
				if(type.equals("ARTIST")) {
					List<String> artistStrings = JSONFetcher.andListOp(
							JSONFetcher.artistsToString(response1.response.artists)
							, JSONFetcher.artistsToString(response2.response.artists));

					response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
					responses.push(response1);
				}
				else if (type.equals("GENRE")) {
					List<String> genreStrings = JSONFetcher.andListOp(JSONFetcher.
							genresToString(response1.response.genres)
							, JSONFetcher.genresToString(response2.response.genres));
					response1.response.genres = JSONFetcher.stringsToGenres(genreStrings);
					responses.push(response1);
				}
				else if (type.equals("ALBUM")) {
					response1.data = JSONFetcher.andListOp(response1.data, response2.data);
					responses.push(response1);
				}
			}
			
			/* Handle OP op */
			else if (node.getOp() == DSLParser.OR) {
				if(type.equals("ARTIST")) {
					List<String> artistStrings = JSONFetcher.orListOp(JSONFetcher.
							artistsToString(response1.response.artists)
							, JSONFetcher.artistsToString(response2.response.artists));

					response1.response.artists = JSONFetcher.stringsToArtists(artistStrings);
					responses.push(response1);
				} 
				else if (type.equals("GENRE")) {
					List<String> genreStrings = JSONFetcher.orListOp(JSONFetcher.
							genresToString(response1.response.genres)
							, JSONFetcher.genresToString(response2.response.genres));
					response1.response.genres = JSONFetcher.stringsToGenres(genreStrings);
					responses.push(response1);
				}
				else if(type.equals("ALBUM")) {
					response1.data = JSONFetcher.orListOp(response1.data, response2.data);
					responses.push(response1);
				}
			}
		}

		else { // single Query
			node.getExpr1().accept(this);
			ResponseHolder response1 = responses.pop();
			responses.push(response1);
			
//			if ((node.getExpr1().type.toString().equals("GENRE")) || 
//					(node.getExpr1().type.toString().equals("ARTIST" ))) {
//			
//			}
//			else if (node.getExpr1().type.toString().equals("ALBUM")) {
//
//			}
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
