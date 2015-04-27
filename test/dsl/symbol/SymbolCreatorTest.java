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

import static org.junit.Assert.*;
import org.antlr.v4.runtime.*;
import org.junit.*;
import dsl.ast.*;
import dsl.lexparse.DSLParser;
import dsl.utility.DSLFactory;
import static dsl.ast.ASTNodeFactory.*;
import static dsl.utility.DSLType.*;

/**
 * Description
 * @version Feb 15, 2015
 */
public class SymbolCreatorTest
{
	private DSLParser parser;
	private ParserRuleContext tree;
	private ASTCreator creator;
	private ASTNode ast;
	private SymbolCreator symbolCreator;
	private SymbolTableManager stm;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		stm = SymbolTableManager.getInstance();
		stm.reset();
	}

	@Test
	public void artistSameGenre()
	{
		doSymbol("artist.sameGenre(\"Nirvana\")");
		Symbol s = stm.getSymbol("\"Nirvana\"");
		assertEquals(ARTIST, s.getType());
	}
	@Test
	public void SongSameGenre()
	{
		doSymbol("song.sameGenre(\"Money Tree\")");
		Symbol s = stm.getSymbol("\"Money Tree\"");
		assertEquals(SONG, s.getType());
	}
	
	@Test
	public void SimilarToAlbum()
	{
		doSymbol("album.similarTo(\"G.K.M.C.\")");
		Symbol s = stm.getSymbol("\"G.K.M.C.\"");
		assertEquals(ALBUM, s.getType());
	}
	

	// Helper methods
	private void makeParser(String inputText)
	{
		parser = DSLFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks like, use <br>
	 * <code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * 
	 * @param inputText
	 *            the text to parse
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
		creator = new ASTCreator();
		ast = tree.accept(creator);
	}
	
	private void doSymbol(String inputText)
	{
		doAST(inputText);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
	}
}
