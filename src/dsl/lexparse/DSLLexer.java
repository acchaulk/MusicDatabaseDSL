// Generated from DSL.g4 by ANTLR 4.5
package dsl.lexparse;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DSLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, LPAR=3, RPAR=4, DOT=5, ARTIST=6, SONG=7, ALBUM=8, GENRE=9, 
		SIMILARTO=10, SAMEDECADE=11, SAMEGENRE=12, CREATE=13, YEAR=14, YEAR_STRING=15, 
		STRING_NONUM=16, STRING=17, INTEGER=18, WS=19, ID=20;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"AND", "OR", "LPAR", "RPAR", "DOT", "ARTIST", "SONG", "ALBUM", "GENRE", 
		"SIMILARTO", "SAMEDECADE", "SAMEGENRE", "CREATE", "YEAR", "YEAR_STRING", 
		"STRING_NONUM", "STRING", "INTEGER", "WS", "ESCAPED_QUOTE", "LETTER", 
		"DIGIT", "ID"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'&'", "'|'", "'('", "')'", "'.'", "'artist'", "'song'", "'album'", 
		"'genre'", "'similarTo'", "'sameDecade'", "'sameGenre'", "'create'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "OR", "LPAR", "RPAR", "DOT", "ARTIST", "SONG", "ALBUM", "GENRE", 
		"SIMILARTO", "SAMEDECADE", "SAMEGENRE", "CREATE", "YEAR", "YEAR_STRING", 
		"STRING_NONUM", "STRING", "INTEGER", "WS", "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DSLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DSL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\26\u00b8\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5"+
		"\17\u0083\n\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\7\21\u008c\n\21\f\21"+
		"\16\21\u008f\13\21\3\21\3\21\3\22\3\22\3\22\7\22\u0096\n\22\f\22\16\22"+
		"\u0099\13\22\3\22\3\22\3\23\6\23\u009e\n\23\r\23\16\23\u009f\3\24\6\24"+
		"\u00a3\n\24\r\24\16\24\u00a4\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\30\3\30\7\30\u00b4\n\30\f\30\16\30\u00b7\13\30\4\u008d"+
		"\u0097\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\2+\2-\2/\26\3\2\b\5\2\f\f\17\17\62;"+
		"\4\2\f\f\17\17\5\2\13\f\17\17\"\"\4\2C\\c|\3\2\62;\4\2AAaa\u00be\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2\2"+
		"\2\t\67\3\2\2\2\139\3\2\2\2\r;\3\2\2\2\17B\3\2\2\2\21G\3\2\2\2\23M\3\2"+
		"\2\2\25S\3\2\2\2\27]\3\2\2\2\31h\3\2\2\2\33r\3\2\2\2\35\u0082\3\2\2\2"+
		"\37\u0084\3\2\2\2!\u0088\3\2\2\2#\u0092\3\2\2\2%\u009d\3\2\2\2\'\u00a2"+
		"\3\2\2\2)\u00a8\3\2\2\2+\u00ab\3\2\2\2-\u00ad\3\2\2\2/\u00af\3\2\2\2\61"+
		"\62\7(\2\2\62\4\3\2\2\2\63\64\7~\2\2\64\6\3\2\2\2\65\66\7*\2\2\66\b\3"+
		"\2\2\2\678\7+\2\28\n\3\2\2\29:\7\60\2\2:\f\3\2\2\2;<\7c\2\2<=\7t\2\2="+
		">\7v\2\2>?\7k\2\2?@\7u\2\2@A\7v\2\2A\16\3\2\2\2BC\7u\2\2CD\7q\2\2DE\7"+
		"p\2\2EF\7i\2\2F\20\3\2\2\2GH\7c\2\2HI\7n\2\2IJ\7d\2\2JK\7w\2\2KL\7o\2"+
		"\2L\22\3\2\2\2MN\7i\2\2NO\7g\2\2OP\7p\2\2PQ\7t\2\2QR\7g\2\2R\24\3\2\2"+
		"\2ST\7u\2\2TU\7k\2\2UV\7o\2\2VW\7k\2\2WX\7n\2\2XY\7c\2\2YZ\7t\2\2Z[\7"+
		"V\2\2[\\\7q\2\2\\\26\3\2\2\2]^\7u\2\2^_\7c\2\2_`\7o\2\2`a\7g\2\2ab\7F"+
		"\2\2bc\7g\2\2cd\7e\2\2de\7c\2\2ef\7f\2\2fg\7g\2\2g\30\3\2\2\2hi\7u\2\2"+
		"ij\7c\2\2jk\7o\2\2kl\7g\2\2lm\7I\2\2mn\7g\2\2no\7p\2\2op\7t\2\2pq\7g\2"+
		"\2q\32\3\2\2\2rs\7e\2\2st\7t\2\2tu\7g\2\2uv\7c\2\2vw\7v\2\2wx\7g\2\2x"+
		"\34\3\2\2\2yz\5-\27\2z{\5-\27\2{|\5-\27\2|}\5-\27\2}\u0083\3\2\2\2~\177"+
		"\5-\27\2\177\u0080\5-\27\2\u0080\u0081\7u\2\2\u0081\u0083\3\2\2\2\u0082"+
		"y\3\2\2\2\u0082~\3\2\2\2\u0083\36\3\2\2\2\u0084\u0085\7$\2\2\u0085\u0086"+
		"\5\35\17\2\u0086\u0087\7$\2\2\u0087 \3\2\2\2\u0088\u008d\7$\2\2\u0089"+
		"\u008c\5)\25\2\u008a\u008c\n\2\2\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2"+
		"\2\2\u008c\u008f\3\2\2\2\u008d\u008e\3\2\2\2\u008d\u008b\3\2\2\2\u008e"+
		"\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7$\2\2\u0091\"\3\2\2\2"+
		"\u0092\u0097\7$\2\2\u0093\u0096\5)\25\2\u0094\u0096\n\3\2\2\u0095\u0093"+
		"\3\2\2\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0098\3\2\2\2\u0097"+
		"\u0095\3\2\2\2\u0098\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7$"+
		"\2\2\u009b$\3\2\2\2\u009c\u009e\5-\27\2\u009d\u009c\3\2\2\2\u009e\u009f"+
		"\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0&\3\2\2\2\u00a1"+
		"\u00a3\t\4\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a2\3\2"+
		"\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\b\24\2\2\u00a7"+
		"(\3\2\2\2\u00a8\u00a9\7^\2\2\u00a9\u00aa\7$\2\2\u00aa*\3\2\2\2\u00ab\u00ac"+
		"\t\5\2\2\u00ac,\3\2\2\2\u00ad\u00ae\t\6\2\2\u00ae.\3\2\2\2\u00af\u00b5"+
		"\5+\26\2\u00b0\u00b4\5+\26\2\u00b1\u00b4\5-\27\2\u00b2\u00b4\t\7\2\2\u00b3"+
		"\u00b0\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b2\3\2\2\2\u00b4\u00b7\3\2"+
		"\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\60\3\2\2\2\u00b7\u00b5"+
		"\3\2\2\2\f\2\u0082\u008b\u008d\u0095\u0097\u009f\u00a4\u00b3\u00b5\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}