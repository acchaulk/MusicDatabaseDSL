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

import static org.junit.Assert.*;

import org.antlr.v4.runtime.*;
import org.junit.*;

import dsl.ast.*;
import dsl.lexparse.DSLParser;
import dsl.lexparse.DSLParserException;
import dsl.semantic.DSLSemanticException;
import dsl.semantic.TypeChecker;
import dsl.symbol.*;
import dsl.utility.*;
import static dsl.utility.DSLType.*;

/**
 * Description
 * 
 * @version Feb 17, 2015
 */

public class TypeCheckerTest {
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
	public void setUp() throws Exception {
		stm = SymbolTableManager.getInstance();
		stm.reset();
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testTypeMixup1() {
		doTypeCheck("artist.sameDecade(\"Nirvana\") & song.similarTo(\"Metallica\")");
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testTypeMixup2() {
		doTypeCheck("song.sameDecade(\"Nirvana\") & album.similarTo(\"Metallica\")");
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testTypeMixup3() {
		doTypeCheck("album.sameDecade(\"Nirvana\") & artist.sameDecade(\"Metallica\")");
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testTypeMixup4() {
		doTypeCheck("album.similarTo(\"Nirvana\") & artist.sameDecade(\"1990\") "
				+ "| song.sameDecade(\"One\")");
	}
	
	@Test
	public void testWorkingQuery1() {
		doTypeCheck("artist.sameDecade(\"Nirvana\") & artist.similarTo(\"Nirvana\")");
	}
	
	@Test
	public void testIfGenreIsAString() {
		doTypeCheck("artist.sameGenre(\"Alt Rock\")");
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testIfGenreIsNotAString1() {
		doTypeCheck("artist.sameGenre(\"22\")");
	}
	
	@Test(expected = DSLSemanticException.class)
	public void testIfGenreIsNotAString2() {
		doTypeCheck("artist.sameGenre(\"Hello123Goodbye\")");
	}
	


	// Helper methods
	private void makeParser(String inputText) {
		parser = DSLFactory.makeParser(new ANTLRInputStream(inputText));
	}

	/**
	 * This method performs the parse. If you want to see what the tree looks
	 * like, use <br>
	 * <code>System.out.println(tree.toStringTree());<code></br>
	 * after calling this method.
	 * 
	 * @param inputText
	 *            the text to parse
	 */
	private String doParse(String inputText) {
		makeParser("create playlist " + inputText);
		tree = parser.dslText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}

	private void doAST(String inputText) {
		doParse(inputText);
		creator = new ASTCreator();
		ast = tree.accept(creator);
	}

	private void doTypeCheck(String inputText) {
		doAST(inputText);
		symbolCreator = new SymbolCreator();
		ast.accept(symbolCreator);
		TypeChecker checker = new TypeChecker();
		do {
			ast.accept(checker);
		} while (checker.checkAgain());
	}
}
