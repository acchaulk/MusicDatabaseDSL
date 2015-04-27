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

import static org.junit.Assert.*;
import org.antlr.v4.runtime.*;
import org.junit.*;
import dsl.lexparse.DSLParser;
import dsl.utility.*;
import static dsl.ast.ASTNodeFactory.*;
import static dsl.lexparse.DSLParser.*;
import static dsl.utility.DSLType.*;

/**
 * Test cases for the ASTNode and ASTCreator classes.
 * @version Feb 14, 2015
 */
public class ASTCreatorTest
{
	private DSLParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode root;
	
//	@Test
//	public void simpleArtistSimilarTo()
//	{
//		doAST("artist.similarTo(\"Nirvana\")");
//		String s = "(CREATEPLAYLIST type=UNDEFINED, name=playlist (QUERY type=ARTIST "
//				+ "(QUERYSTRING type=UNDEFINED, token=\"Nirvana\")))";
//		assertEquals(s, root.toString());
//	}
	
	@Test
	public void ComplexeArtistSimilarToANDGenreORSameDecade()
	{
		doAST("artist.similarTo(\"30 Seconds to Mars\") | artist.sameGenre(\"Rock\") | artist.sameDecade(\"Metallica\")");
	}
//	@Test
//	public void simpleArtistSimilarToANDGenre()
//	{
//		doAST("artist.similarTo(\"30 Seconds to Mars\") & artist.sameGenre(\"Rock\")");
//		String s = "(CREATEPLAYLIST type=UNDEFINED, name=playlist (QUERYLIST type=UNDEFINED, token=& (QUERY type=ARTIST (QUERYSTRING type=UNDEFINED, token=\"30 Seconds to Mars\")) (QUERY type=ARTIST (QUERYSTRING type=UNDEFINED, token=\"Rock\"))))";
//		assertEquals(s, root.toString());
//	}
//	
//	
//	@Test
//	public void simpleArtistSimilarToORGenre()
//	{
//		doAST("artist.similarTo(\"Nirvana\") | artist.sameGenre(\"Rock\")");
//		String s = "(CREATEPLAYLIST type=UNDEFINED, name=playlist (QUERYLIST type=UNDEFINED, token=| (QUERY type=ARTIST (QUERYSTRING type=UNDEFINED, token=\"Nirvana\")) (QUERY type=ARTIST (QUERYSTRING type=UNDEFINED, token=\"Rock\"))))";
//		assertEquals(s, root.toString());
//	}
	

	// Helper methods
	private void makeParser(String inputText)
	{
		parser = DSLFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks like, use
	 * 		<br><code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * @param inputText the text to parse
	 */
	private String doParse(String inputText)
	{
		makeParser("create playlist " + inputText);
		tree = parser.createPlaylist();
		assertTrue(true);
		return tree.toStringTree(parser);
	}
	
	private void doAST(String inputText)
	{
		doParse(inputText);
		creator =  new ASTCreator();
		root = tree.accept(creator);
	}
}
