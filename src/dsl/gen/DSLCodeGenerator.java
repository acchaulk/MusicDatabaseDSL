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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.boon.json.serializers.impl.JsonSimpleSerializerImpl;

import json.templates.*;
import json.templates.albums.Data;
import json.templates.song.Song;
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
	//		create p (artist.sameGenre("Pop") & artist.sameDecade("1990")) | artist.sameGenre("rock")
	//		create p genre.similarTo("metal")
	// 		create p album.similarTo("Nevermind") | album.similarTo("Ten")
	//		create p album.similarTo("Britney") & album.sameGenre("pop")
	// 		create p song.similarTo("Kendrick Lamar King Kunta") & song.sameGenre("rap")

	
	
	// create playlist artist.similarTo("Nirvana") & artist.similarTo("Foo Fighters")
	// create playlist artist.similarTo("britney spears") & artist.sameGenre("Pop") & artist.sameDecade("1990")
	// create p genre.similarTo("metal")
	// create playlist song.similarTo("Nirvana Smells Like Teen Spirit") & song.sameGenre("grunge")

	public Stack<ResponseHolder> responses;
	public List<String> excluded;
	public String type;

	public DSLCodeGenerator() {
		responses = new Stack<ResponseHolder>();
		excluded = new ArrayList<String>();

	}

	public String visit(CreatePlaylistNode node) {
		visitChildren(node); // main entry point

		if(responses.size() == 1) {
			ResponseHolder r = responses.peek();
			if(type.equals("ARTIST")) {
				List<String> artistStrings = JSONFetcher.artistsToString(r.response.artists);
				if(r.response.artists.size() > 0) {
					System.out.println("--Here are some suggested artists for you--");
					for(String a : excluded) {
						artistStrings.remove(a);
					}
					for(String a : artistStrings) {
						System.out.println(a.toString());
					}
				}
				else {
					System.out.println("--I could not find any similar artists for you--");
				}
			} 

			else if(type.equals("GENRE")) {
				List<String> genreStrings = JSONFetcher.genresToString(r.response.genres);
				if(r.response.artists.size() > 0) {
					System.out.println("--Here are some suggested genres for you--");
					for(String a : excluded) {
						genreStrings.remove(a);
					}
					for(String a : genreStrings) {
						System.out.println(a.toString());
					}
				}
				else {
					System.out.println("--I could not find any similar genres for you--");
				}
			}

			else if(type.equals("ALBUM")) {
				if(r.data.size() > 0) {
					System.out.println("--Here are some suggested albums for you--");
					for(Data d : r.data) {
						if(!excluded.contains(d.title)) {
							System.out.println("Album Name: " + d.title + ", Artist Name: " + d.performer_name);
						}
					}
				}
				else {
					System.out.println("--I could not find any similar albums for you--");
				}
			}
			else if(type.equals("SONG")) {
				if(r.response.songs.length > 0) {
					System.out.println(r.response.songs.length);
					System.out.println("--Here are some suggested songs for you--");
					for(Song s : r.response.songs) {
//						if(!excluded.contains(s.title)) {
						if(s != null)
							System.out.println("Song Name: " + s.title + ", Artist Name: " + s.artist_name);
//						}
					}
				}
				else {
					System.out.println("--I could not find any similar songs for you--");
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
					if(node.getExpr1().children.size() == 1 && node.getExpr2().children.size() == 1) {
						//					if(node.getExpr1().nodeType.equals(QUERY) & node.getExpr2().nodeType.equals(QUERY)) {
						String s1 = ((QueryNode) node.getExpr1().getChild(0)).getFunction().token.getText().toLowerCase();
						String s2 = ((QueryNode) node.getExpr2().getChild(0)).getFunction().token.getText().toLowerCase();
						String q1 = ((QueryNode) node.getExpr1().getChild(0)).getQueryStringNode().token.getText();
						String q2 = ((QueryNode) node.getExpr2().getChild(0)).getQueryStringNode().token.getText();

						ResponseHolder r = combinedAlbumQuery(s1, s2, q1, q2);
						//						System.out.println("R SIZE" + r.data.size());
						if(r == null) {
							response1.data = JSONFetcher.andListOp(response1.data, response2.data);
							responses.push(response1);
						}
						else {
							responses.push(r);
						}
					}
					else {
						response1.data = JSONFetcher.andListOp(response1.data, response2.data);
						for(Data d : response1.data) {
							System.out.println("Data " + d.id);
						}
						responses.push(response1);
					}
				}
				else if(type.equals("SONG")) {
					response1.response.songs = JSONFetcher.andArrayOp(response1.response.songs, response2.response.songs);
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

				else if(type.equals("SONG")) {
					//					response1.response.songs = null;
					response1.response.songs = JSONFetcher.orArrayOp(response1.response.songs, response2.response.songs);
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

	private ResponseHolder combinedAlbumQuery(String s1, String s2, String q1, String q2) {
		ResponseHolder r = null;
		if(s1.equals("samegenre")){
			if(s2.equals("samedecade")) {

				r = JSONFetcher.albumCombinedQueries(q1, "", q2);
			}
			else if(s2.equals("similarto")) {
				r = JSONFetcher.albumCombinedQueries(q1, q2, "");
			}

		}
		else if(s1.equals("samedecade")) {
			if(s2.equals("similarto")) {
				r = JSONFetcher.albumCombinedQueries("", q2, q1);
			}
			else if(s2.equals("samegenre")) {
				r = JSONFetcher.albumCombinedQueries(q2, "", q1);
			}

		}
		else if(s1.equals("similarto")) {
			if(s2.equals("samedecade")) {
				r = JSONFetcher.albumCombinedQueries("", q1, q2);
			}
			else if(s2.equals("samegenre")) {
				r = JSONFetcher.albumCombinedQueries(q2, q1, "");
			}
		}
		return r;
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
