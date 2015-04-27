package dsl.lexparse;

import org.antlr.v4.runtime.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static dsl.lexparse.DSLLexer.*;
import dsl.utility.DSLFactory;

/**
 * Description
 * @version Jan 26, 2015
 */
public class DSLLexerTest
{
	private DSLLexer lexer;
	private Token t;
	
	@Test
	public void recognizeReservedWords()
	{
		makeLexer("artist song album similarTo sameDecade sameGenre");
		checkNextToken(ARTIST, "artist");
		checkNextToken(SONG, "song");
		checkNextToken(ALBUM, "album");
		checkNextToken(SIMILARTO, "similarTo");
		checkNextToken(SAMEDECADE, "sameDecade");
		checkNextToken(SAMEGENRE, "sameGenre");
	}
	
	@Test
	public void recognizeSeparators()
	{
		makeLexer("&|().");
		checkNextToken(AND, "&");
		checkNextToken(OR, "|");
		checkNextToken(LPAR, "(");
		checkNextToken(RPAR, ")");
		checkNextToken(DOT, ".");
		nextToken();
		assertEquals(EOF, t.getType());
	}
	
	
	@Test
	public void recognizeSimpleStrings()
	{
		makeLexer(" \"hello\" \"Test1\" \"1_var\" \"1990\" \"80s\"");
		checkNextToken(STRING_NONUM, "\"hello\"");
		checkNextToken(STRING, "\"Test1\"");
		checkNextToken(STRING, "\"1_var\"");
		checkNextToken(YEAR_STRING, "\"1990\"");
		checkNextToken(YEAR_STRING, "\"80s\"");
	}
	
	// Helper methods
	private void makeLexer(String text)
	{
		lexer = DSLFactory.makeLexer(new ANTLRInputStream(text));
	}

	private void nextToken()
	{
		t = lexer.nextToken();
	}

	private void checkNextToken(int tokenType, String tokenText)
	{
		nextToken();
		assertEquals(tokenType, t.getType());
		assertEquals(tokenText, t.getText());
	}
}