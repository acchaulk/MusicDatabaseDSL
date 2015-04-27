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

package dsl.lexparse;

import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.gui.TreeViewer;
import org.junit.Test;
import dsl.utility.DSLFactory;

/**
 * Description
 * @version Jan 27, 2015
 */
public class DSLParserTest
{
	private DSLParser parser;
	private ParserRuleContext tree;
	
	@Test
	public void recognizeAristSimilarTo() {
		doParse("artist.similarTo(\"Nirvana\")");
	}
	
	@Test
	public void recognizeAlbumSimilarTo() {
		doParse("album.similarTo(\"Good Kid Mad City\")");
	}
	
	@Test
	public void recognizeSongSimilarGenrea() {
		doParse("song.sameGenre(\"Money Trees\") &"
				+ "artist.sameDecade(\"Kendrick Lamar\")");
	}
	
	
	
	
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
		tree = parser.dslText();
		assertTrue(true);
		return tree.toStringTree(parser);
	}
	
	/**
	 * Call this routine to display the parse tree. Hit ENTER on the console to continue.
	 */
	private void showTree()
	{
		System.out.println(tree.toStringTree(parser));
		List<String> ruleNames = Arrays.asList(parser.getRuleNames());
		TreeViewer tv = new TreeViewer(ruleNames, tree);
		JFrame frame = new JFrame("Parse Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tv);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        BufferedReader br = 
                new BufferedReader(new InputStreamReader(System.in));
        try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
