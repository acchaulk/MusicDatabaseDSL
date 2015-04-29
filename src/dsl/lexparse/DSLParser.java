// Generated from DSL.g4 by ANTLR 4.5
package dsl.lexparse;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DSLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, LPAR=3, RPAR=4, DOT=5, ARTIST=6, SONG=7, ALBUM=8, GENRE=9, 
		SIMILARTO=10, SAMEDECADE=11, SAMEGENRE=12, CREATE=13, YEAR=14, YEAR_STRING=15, 
		STRING_NONUM=16, STRING=17, INTEGER=18, WS=19, ID=20;
	public static final int
		RULE_dslText = 0, RULE_createPlaylist = 1, RULE_queryList = 2, RULE_query = 3, 
		RULE_queryString = 4, RULE_function = 5, RULE_type = 6;
	public static final String[] ruleNames = {
		"dslText", "createPlaylist", "queryList", "query", "queryString", "function", 
		"type"
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

	@Override
	public String getGrammarFileName() { return "DSL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DSLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DslTextContext extends ParserRuleContext {
		public CreatePlaylistContext createPlaylist() {
			return getRuleContext(CreatePlaylistContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DSLParser.EOF, 0); }
		public DslTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dslText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterDslText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitDslText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitDslText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DslTextContext dslText() throws RecognitionException {
		DslTextContext _localctx = new DslTextContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dslText);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			createPlaylist();
			setState(15);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreatePlaylistContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(DSLParser.CREATE, 0); }
		public TerminalNode ID() { return getToken(DSLParser.ID, 0); }
		public QueryListContext queryList() {
			return getRuleContext(QueryListContext.class,0);
		}
		public CreatePlaylistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createPlaylist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterCreatePlaylist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitCreatePlaylist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitCreatePlaylist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreatePlaylistContext createPlaylist() throws RecognitionException {
		CreatePlaylistContext _localctx = new CreatePlaylistContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_createPlaylist);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			match(CREATE);
			setState(18);
			match(ID);
			setState(19);
			queryList(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryListContext extends ParserRuleContext {
		public TerminalNode LPAR() { return getToken(DSLParser.LPAR, 0); }
		public List<QueryListContext> queryList() {
			return getRuleContexts(QueryListContext.class);
		}
		public QueryListContext queryList(int i) {
			return getRuleContext(QueryListContext.class,i);
		}
		public TerminalNode RPAR() { return getToken(DSLParser.RPAR, 0); }
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public TerminalNode AND() { return getToken(DSLParser.AND, 0); }
		public TerminalNode OR() { return getToken(DSLParser.OR, 0); }
		public QueryListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterQueryList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitQueryList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitQueryList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryListContext queryList() throws RecognitionException {
		return queryList(0);
	}

	private QueryListContext queryList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		QueryListContext _localctx = new QueryListContext(_ctx, _parentState);
		QueryListContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_queryList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			switch (_input.LA(1)) {
			case LPAR:
				{
				setState(22);
				match(LPAR);
				setState(23);
				queryList(0);
				setState(24);
				match(RPAR);
				}
				break;
			case ARTIST:
			case SONG:
			case ALBUM:
			case GENRE:
				{
				setState(26);
				query();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(37);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(35);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new QueryListContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_queryList);
						setState(29);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(30);
						match(AND);
						setState(31);
						queryList(4);
						}
						break;
					case 2:
						{
						_localctx = new QueryListContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_queryList);
						setState(32);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(33);
						match(OR);
						setState(34);
						queryList(3);
						}
						break;
					}
					} 
				}
				setState(39);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode DOT() { return getToken(DSLParser.DOT, 0); }
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(DSLParser.LPAR, 0); }
		public QueryStringContext queryString() {
			return getRuleContext(QueryStringContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(DSLParser.RPAR, 0); }
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			type();
			setState(41);
			match(DOT);
			setState(42);
			function();
			setState(43);
			match(LPAR);
			setState(44);
			queryString();
			setState(45);
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryStringContext extends ParserRuleContext {
		public TerminalNode YEAR_STRING() { return getToken(DSLParser.YEAR_STRING, 0); }
		public TerminalNode STRING_NONUM() { return getToken(DSLParser.STRING_NONUM, 0); }
		public TerminalNode STRING() { return getToken(DSLParser.STRING, 0); }
		public QueryStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterQueryString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitQueryString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitQueryString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryStringContext queryString() throws RecognitionException {
		QueryStringContext _localctx = new QueryStringContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_queryString);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << YEAR_STRING) | (1L << STRING_NONUM) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode SAMEDECADE() { return getToken(DSLParser.SAMEDECADE, 0); }
		public TerminalNode SIMILARTO() { return getToken(DSLParser.SIMILARTO, 0); }
		public TerminalNode SAMEGENRE() { return getToken(DSLParser.SAMEGENRE, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SIMILARTO) | (1L << SAMEDECADE) | (1L << SAMEGENRE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode ARTIST() { return getToken(DSLParser.ARTIST, 0); }
		public TerminalNode ALBUM() { return getToken(DSLParser.ALBUM, 0); }
		public TerminalNode SONG() { return getToken(DSLParser.SONG, 0); }
		public TerminalNode GENRE() { return getToken(DSLParser.GENRE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DSLListener ) ((DSLListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DSLVisitor ) return ((DSLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ARTIST) | (1L << SONG) | (1L << ALBUM) | (1L << GENRE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return queryList_sempred((QueryListContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean queryList_sempred(QueryListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\268\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\5\4\36\n\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4&\n\4\f"+
		"\4\16\4)\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b"+
		"\2\3\6\t\2\4\6\b\n\f\16\2\5\3\2\21\23\3\2\f\16\3\2\b\13\63\2\20\3\2\2"+
		"\2\4\23\3\2\2\2\6\35\3\2\2\2\b*\3\2\2\2\n\61\3\2\2\2\f\63\3\2\2\2\16\65"+
		"\3\2\2\2\20\21\5\4\3\2\21\22\7\2\2\3\22\3\3\2\2\2\23\24\7\17\2\2\24\25"+
		"\7\26\2\2\25\26\5\6\4\2\26\5\3\2\2\2\27\30\b\4\1\2\30\31\7\5\2\2\31\32"+
		"\5\6\4\2\32\33\7\6\2\2\33\36\3\2\2\2\34\36\5\b\5\2\35\27\3\2\2\2\35\34"+
		"\3\2\2\2\36\'\3\2\2\2\37 \f\5\2\2 !\7\3\2\2!&\5\6\4\6\"#\f\4\2\2#$\7\4"+
		"\2\2$&\5\6\4\5%\37\3\2\2\2%\"\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2"+
		"(\7\3\2\2\2)\'\3\2\2\2*+\5\16\b\2+,\7\7\2\2,-\5\f\7\2-.\7\5\2\2./\5\n"+
		"\6\2/\60\7\6\2\2\60\t\3\2\2\2\61\62\t\2\2\2\62\13\3\2\2\2\63\64\t\3\2"+
		"\2\64\r\3\2\2\2\65\66\t\4\2\2\66\17\3\2\2\2\5\35%\'";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}