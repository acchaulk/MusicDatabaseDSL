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

package dsl.utility;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import dsl.lexparse.DSLLexer;
import dsl.lexparse.DSLLexerException;
import dsl.lexparse.DSLParser;
import dsl.lexparse.DSLParserException;

/**
 * The DijkstraFactory is responsible for constructing all, or parts of a Dijkstra
 * compiler. It is a standard Factory class.
 * 
 * @version Jan 26, 2015
 */
public class DSLFactory
{
	/**
	 * Create a Dijkstra lexer using the specified input stream containing the text
	 * @param inputText the ANTLRInputStream that contains the program text
	 * @return the Dijkstra lexer
	 */
	static public DSLLexer makeLexer(ANTLRInputStream inputText) {
		final DSLLexer lexer = new DSLLexer(inputText);
		lexer.addErrorListener(
				new BaseErrorListener() {
					@Override
					public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
							int line, int charPositionInLine, String msg,
							RecognitionException e)
					{
						throw new DSLLexerException(msg, e);
					}
				}
		);
		return lexer;
	}
	
	/**
	 * @param inputText
	 * @return
	 */
	static public DSLParser makeParser(ANTLRInputStream inputText) {
		final DSLLexer lexer = makeLexer(inputText);
		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final DSLParser parser = new DSLParser(tokenStream);
		parser.addErrorListener(
				new BaseErrorListener() {
					@Override
					public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
							int line, int charPositionInLine, String msg,
							RecognitionException e)
					{
						throw new DSLParserException(e.getMessage(), e);
					}
				}
		);
		return parser;
	}
}
