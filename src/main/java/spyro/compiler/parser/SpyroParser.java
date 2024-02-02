// Generated from Spyro.g4 by ANTLR 4.13.1

package spyro.compiler.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class SpyroParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, VARIABLES=3, SIGNATURES=4, LANGUAGE=5, EXAMPLES=6, ASSUMPTIONS=7, 
		OR=8, AND=9, EQ=10, NEQ=11, GT=12, LT=13, GTEQ=14, LTEQ=15, PLUS=16, MINUS=17, 
		MULT=18, DIV=19, MOD=20, NOT=21, SEMI=22, ASSIGN=23, LPAREN=24, RPAREN=25, 
		LBRACE=26, RBRACE=27, ARROW=28, HIDDENVAR=29, TRUE=30, FALSE=31, NULL=32, 
		HOLE=33, ID=34, INT=35, FLOAT=36, STRING=37, COMMENT=38, SPACE=39, OTHER=40;
	public static final int
		RULE_parse = 0, RULE_program = 1, RULE_declVariables = 2, RULE_declVar = 3, 
		RULE_declSignatures = 4, RULE_declSig = 5, RULE_declLanguage = 6, RULE_declLanguageRule = 7, 
		RULE_declExamples = 8, RULE_declExampleRule = 9, RULE_declAssumptions = 10, 
		RULE_declAssumption = 11, RULE_type = 12, RULE_expr = 13, RULE_atom = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "program", "declVariables", "declVar", "declSignatures", "declSig", 
			"declLanguage", "declLanguageRule", "declExamples", "declExampleRule", 
			"declAssumptions", "declAssumption", "type", "expr", "atom"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'|'", "','", "'variables'", "'signatures'", "'language'", "'examples'", 
			"'assumptions'", "'||'", "'&&'", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "';'", "'='", "'('", 
			"')'", "'{'", "'}'", "'->'", "'hidden'", "'true'", "'false'", "'null'", 
			"'??'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "VARIABLES", "SIGNATURES", "LANGUAGE", "EXAMPLES", 
			"ASSUMPTIONS", "OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", 
			"PLUS", "MINUS", "MULT", "DIV", "MOD", "NOT", "SEMI", "ASSIGN", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "ARROW", "HIDDENVAR", "TRUE", "FALSE", 
			"NULL", "HOLE", "ID", "INT", "FLOAT", "STRING", "COMMENT", "SPACE", "OTHER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "Spyro.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SpyroParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SpyroParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			program();
			setState(31);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public DeclVariablesContext declVariables() {
			return getRuleContext(DeclVariablesContext.class,0);
		}
		public DeclSignaturesContext declSignatures() {
			return getRuleContext(DeclSignaturesContext.class,0);
		}
		public DeclLanguageContext declLanguage() {
			return getRuleContext(DeclLanguageContext.class,0);
		}
		public DeclExamplesContext declExamples() {
			return getRuleContext(DeclExamplesContext.class,0);
		}
		public DeclAssumptionsContext declAssumptions() {
			return getRuleContext(DeclAssumptionsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			declVariables();
			setState(34);
			declSignatures();
			setState(35);
			declLanguage();
			setState(36);
			declExamples();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSUMPTIONS) {
				{
				setState(37);
				declAssumptions();
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclVariablesContext extends ParserRuleContext {
		public TerminalNode VARIABLES() { return getToken(SpyroParser.VARIABLES, 0); }
		public TerminalNode LBRACE() { return getToken(SpyroParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(SpyroParser.RBRACE, 0); }
		public List<DeclVarContext> declVar() {
			return getRuleContexts(DeclVarContext.class);
		}
		public DeclVarContext declVar(int i) {
			return getRuleContext(DeclVarContext.class,i);
		}
		public DeclVariablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declVariables; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclVariables(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclVariablesContext declVariables() throws RecognitionException {
		DeclVariablesContext _localctx = new DeclVariablesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declVariables);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(VARIABLES);
			setState(41);
			match(LBRACE);
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				declVar();
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==HIDDENVAR || _la==ID );
			setState(47);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclVarContext extends ParserRuleContext {
		public DeclVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declVar; }
	 
		public DeclVarContext() { }
		public void copyFrom(DeclVarContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclHiddenVarContext extends DeclVarContext {
		public TerminalNode HIDDENVAR() { return getToken(SpyroParser.HIDDENVAR, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclHiddenVarContext(DeclVarContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclHiddenVar(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclVisibleVarContext extends DeclVarContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclVisibleVarContext(DeclVarContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclVisibleVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclVarContext declVar() throws RecognitionException {
		DeclVarContext _localctx = new DeclVarContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declVar);
		try {
			setState(58);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new DeclVisibleVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				type();
				setState(50);
				match(ID);
				setState(51);
				match(SEMI);
				}
				break;
			case HIDDENVAR:
				_localctx = new DeclHiddenVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				match(HIDDENVAR);
				setState(54);
				type();
				setState(55);
				match(ID);
				setState(56);
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclSignaturesContext extends ParserRuleContext {
		public TerminalNode SIGNATURES() { return getToken(SpyroParser.SIGNATURES, 0); }
		public TerminalNode LBRACE() { return getToken(SpyroParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(SpyroParser.RBRACE, 0); }
		public List<DeclSigContext> declSig() {
			return getRuleContexts(DeclSigContext.class);
		}
		public DeclSigContext declSig(int i) {
			return getRuleContext(DeclSigContext.class,i);
		}
		public DeclSignaturesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declSignatures; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclSignatures(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclSignaturesContext declSignatures() throws RecognitionException {
		DeclSignaturesContext _localctx = new DeclSignaturesContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_declSignatures);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(SIGNATURES);
			setState(61);
			match(LBRACE);
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				declSig();
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 67664740352L) != 0) );
			setState(67);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclSigContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclSigContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declSig; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclSig(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclSigContext declSig() throws RecognitionException {
		DeclSigContext _localctx = new DeclSigContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declSig);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			expr(0);
			setState(70);
			match(SEMI);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclLanguageContext extends ParserRuleContext {
		public TerminalNode LANGUAGE() { return getToken(SpyroParser.LANGUAGE, 0); }
		public TerminalNode LBRACE() { return getToken(SpyroParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(SpyroParser.RBRACE, 0); }
		public List<DeclLanguageRuleContext> declLanguageRule() {
			return getRuleContexts(DeclLanguageRuleContext.class);
		}
		public DeclLanguageRuleContext declLanguageRule(int i) {
			return getRuleContext(DeclLanguageRuleContext.class,i);
		}
		public DeclLanguageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declLanguage; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclLanguage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclLanguageContext declLanguage() throws RecognitionException {
		DeclLanguageContext _localctx = new DeclLanguageContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_declLanguage);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(LANGUAGE);
			setState(73);
			match(LBRACE);
			setState(75); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(74);
				declLanguageRule();
				}
				}
				setState(77); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(79);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclLanguageRuleContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode ARROW() { return getToken(SpyroParser.ARROW, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DeclLanguageRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declLanguageRule; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclLanguageRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclLanguageRuleContext declLanguageRule() throws RecognitionException {
		DeclLanguageRuleContext _localctx = new DeclLanguageRuleContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declLanguageRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			type();
			setState(82);
			match(ID);
			setState(83);
			match(ARROW);
			{
			setState(84);
			expr(0);
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(85);
				match(T__0);
				setState(86);
				expr(0);
				}
				}
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(92);
			match(SEMI);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclExamplesContext extends ParserRuleContext {
		public TerminalNode EXAMPLES() { return getToken(SpyroParser.EXAMPLES, 0); }
		public TerminalNode LBRACE() { return getToken(SpyroParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(SpyroParser.RBRACE, 0); }
		public List<DeclExampleRuleContext> declExampleRule() {
			return getRuleContexts(DeclExampleRuleContext.class);
		}
		public DeclExampleRuleContext declExampleRule(int i) {
			return getRuleContext(DeclExampleRuleContext.class,i);
		}
		public DeclExamplesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declExamples; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclExamples(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclExamplesContext declExamples() throws RecognitionException {
		DeclExamplesContext _localctx = new DeclExamplesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_declExamples);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(EXAMPLES);
			setState(95);
			match(LBRACE);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(96);
				declExampleRule();
				}
				}
				setState(101);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(102);
			match(RBRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclExampleRuleContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode ARROW() { return getToken(SpyroParser.ARROW, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DeclExampleRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declExampleRule; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclExampleRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclExampleRuleContext declExampleRule() throws RecognitionException {
		DeclExampleRuleContext _localctx = new DeclExampleRuleContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_declExampleRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			type();
			setState(105);
			match(ID);
			setState(106);
			match(ARROW);
			{
			setState(107);
			expr(0);
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(108);
				match(T__0);
				setState(109);
				expr(0);
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(115);
			match(SEMI);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclAssumptionsContext extends ParserRuleContext {
		public TerminalNode ASSUMPTIONS() { return getToken(SpyroParser.ASSUMPTIONS, 0); }
		public TerminalNode LBRACE() { return getToken(SpyroParser.LBRACE, 0); }
		public DeclAssumptionContext declAssumption() {
			return getRuleContext(DeclAssumptionContext.class,0);
		}
		public TerminalNode RBRACE() { return getToken(SpyroParser.RBRACE, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclAssumptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declAssumptions; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclAssumptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclAssumptionsContext declAssumptions() throws RecognitionException {
		DeclAssumptionsContext _localctx = new DeclAssumptionsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_declAssumptions);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(ASSUMPTIONS);
			setState(118);
			match(LBRACE);
			setState(119);
			declAssumption();
			setState(120);
			match(RBRACE);
			setState(121);
			match(SEMI);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclAssumptionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclAssumptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declAssumption; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclAssumption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclAssumptionContext declAssumption() throws RecognitionException {
		DeclAssumptionContext _localctx = new DeclAssumptionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_declAssumption);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			expr(0);
			setState(124);
			match(SEMI);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(ID);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExprContext {
		public TerminalNode NOT() { return getToken(SpyroParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryMinusExprContext extends ExprContext {
		public TerminalNode MINUS() { return getToken(SpyroParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitUnaryMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicationExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MULT() { return getToken(SpyroParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(SpyroParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(SpyroParser.MOD, 0); }
		public MultiplicationExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitMultiplicationExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExprContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public AtomExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitAtomExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OR() { return getToken(SpyroParser.OR, 0); }
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(SpyroParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(SpyroParser.MINUS, 0); }
		public AdditiveExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitAdditiveExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationalExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LTEQ() { return getToken(SpyroParser.LTEQ, 0); }
		public TerminalNode GTEQ() { return getToken(SpyroParser.GTEQ, 0); }
		public TerminalNode LT() { return getToken(SpyroParser.LT, 0); }
		public TerminalNode GT() { return getToken(SpyroParser.GT, 0); }
		public RelationalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitRelationalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(SpyroParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(SpyroParser.RPAREN, 0); }
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(SpyroParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(SpyroParser.NEQ, 0); }
		public EqualityExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(SpyroParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(SpyroParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FunctionExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitFunctionExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(SpyroParser.AND, 0); }
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(129);
				match(LPAREN);
				setState(130);
				expr(0);
				setState(131);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new FunctionExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				match(ID);
				setState(134);
				match(LPAREN);
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 67664740352L) != 0)) {
					{
					setState(135);
					expr(0);
					setState(140);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(136);
						match(T__1);
						setState(137);
						expr(0);
						}
						}
						setState(142);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(145);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(146);
				match(MINUS);
				setState(147);
				expr(9);
				}
				break;
			case 4:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(148);
				match(NOT);
				setState(149);
				expr(8);
				}
				break;
			case 5:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(150);
				atom();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(171);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(153);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(154);
						((MultiplicationExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1835008L) != 0)) ) {
							((MultiplicationExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(155);
						expr(8);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(156);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(157);
						((AdditiveExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((AdditiveExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(158);
						expr(7);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(159);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(160);
						((RelationalExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 61440L) != 0)) ) {
							((RelationalExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(161);
						expr(6);
						}
						break;
					case 4:
						{
						_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(162);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(163);
						((EqualityExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
							((EqualityExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(164);
						expr(5);
						}
						break;
					case 5:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(165);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(166);
						match(AND);
						setState(167);
						expr(4);
						}
						break;
					case 6:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(168);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(169);
						match(OR);
						setState(170);
						expr(3);
						}
						break;
					}
					} 
				}
				setState(175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanAtomContext extends AtomContext {
		public TerminalNode TRUE() { return getToken(SpyroParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(SpyroParser.FALSE, 0); }
		public BooleanAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitBooleanAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdAtomContext extends AtomContext {
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public IdAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitIdAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SizedHoleAtomContext extends AtomContext {
		public TerminalNode HOLE() { return getToken(SpyroParser.HOLE, 0); }
		public TerminalNode LPAREN() { return getToken(SpyroParser.LPAREN, 0); }
		public TerminalNode INT() { return getToken(SpyroParser.INT, 0); }
		public TerminalNode RPAREN() { return getToken(SpyroParser.RPAREN, 0); }
		public SizedHoleAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitSizedHoleAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnsizedHoleAtomContext extends AtomContext {
		public TerminalNode HOLE() { return getToken(SpyroParser.HOLE, 0); }
		public UnsizedHoleAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitUnsizedHoleAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberAtomContext extends AtomContext {
		public TerminalNode INT() { return getToken(SpyroParser.INT, 0); }
		public NumberAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitNumberAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NullAtomContext extends AtomContext {
		public TerminalNode NULL() { return getToken(SpyroParser.NULL, 0); }
		public NullAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitNullAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_atom);
		int _la;
		try {
			setState(185);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new NumberAtomContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				match(INT);
				}
				break;
			case 2:
				_localctx = new BooleanAtomContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				_la = _input.LA(1);
				if ( !(_la==TRUE || _la==FALSE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 3:
				_localctx = new IdAtomContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(178);
				match(ID);
				}
				break;
			case 4:
				_localctx = new NullAtomContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(179);
				match(NULL);
				}
				break;
			case 5:
				_localctx = new UnsizedHoleAtomContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(180);
				match(HOLE);
				}
				break;
			case 6:
				_localctx = new SizedHoleAtomContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(181);
				match(HOLE);
				setState(182);
				match(LPAREN);
				setState(183);
				match(INT);
				setState(184);
				match(RPAREN);
				}
				break;
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
		case 13:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001(\u00bc\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001\'\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002"+
		",\b\u0002\u000b\u0002\f\u0002-\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003;\b\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0004\u0004@\b\u0004\u000b\u0004\f\u0004A\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0004\u0006L\b\u0006\u000b\u0006\f\u0006M\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005"+
		"\u0007X\b\u0007\n\u0007\f\u0007[\t\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0005\bb\b\b\n\b\f\be\t\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\to\b\t\n\t\f\tr\t\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u008b\b\r\n\r\f\r\u008e\t\r"+
		"\u0003\r\u0090\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u0098\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0005\r\u00ac\b\r\n\r\f\r\u00af\t\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u00ba\b\u000e\u0001\u000e\u0000\u0001\u001a\u000f"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u0000\u0005\u0001\u0000\u0012\u0014\u0001\u0000\u0010\u0011\u0001"+
		"\u0000\f\u000f\u0001\u0000\n\u000b\u0001\u0000\u001e\u001f\u00c5\u0000"+
		"\u001e\u0001\u0000\u0000\u0000\u0002!\u0001\u0000\u0000\u0000\u0004(\u0001"+
		"\u0000\u0000\u0000\u0006:\u0001\u0000\u0000\u0000\b<\u0001\u0000\u0000"+
		"\u0000\nE\u0001\u0000\u0000\u0000\fH\u0001\u0000\u0000\u0000\u000eQ\u0001"+
		"\u0000\u0000\u0000\u0010^\u0001\u0000\u0000\u0000\u0012h\u0001\u0000\u0000"+
		"\u0000\u0014u\u0001\u0000\u0000\u0000\u0016{\u0001\u0000\u0000\u0000\u0018"+
		"~\u0001\u0000\u0000\u0000\u001a\u0097\u0001\u0000\u0000\u0000\u001c\u00b9"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0003\u0002\u0001\u0000\u001f \u0005"+
		"\u0000\u0000\u0001 \u0001\u0001\u0000\u0000\u0000!\"\u0003\u0004\u0002"+
		"\u0000\"#\u0003\b\u0004\u0000#$\u0003\f\u0006\u0000$&\u0003\u0010\b\u0000"+
		"%\'\u0003\u0014\n\u0000&%\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000"+
		"\u0000\'\u0003\u0001\u0000\u0000\u0000()\u0005\u0003\u0000\u0000)+\u0005"+
		"\u001a\u0000\u0000*,\u0003\u0006\u0003\u0000+*\u0001\u0000\u0000\u0000"+
		",-\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000"+
		"\u0000./\u0001\u0000\u0000\u0000/0\u0005\u001b\u0000\u00000\u0005\u0001"+
		"\u0000\u0000\u000012\u0003\u0018\f\u000023\u0005\"\u0000\u000034\u0005"+
		"\u0016\u0000\u00004;\u0001\u0000\u0000\u000056\u0005\u001d\u0000\u0000"+
		"67\u0003\u0018\f\u000078\u0005\"\u0000\u000089\u0005\u0016\u0000\u0000"+
		"9;\u0001\u0000\u0000\u0000:1\u0001\u0000\u0000\u0000:5\u0001\u0000\u0000"+
		"\u0000;\u0007\u0001\u0000\u0000\u0000<=\u0005\u0004\u0000\u0000=?\u0005"+
		"\u001a\u0000\u0000>@\u0003\n\u0005\u0000?>\u0001\u0000\u0000\u0000@A\u0001"+
		"\u0000\u0000\u0000A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000"+
		"BC\u0001\u0000\u0000\u0000CD\u0005\u001b\u0000\u0000D\t\u0001\u0000\u0000"+
		"\u0000EF\u0003\u001a\r\u0000FG\u0005\u0016\u0000\u0000G\u000b\u0001\u0000"+
		"\u0000\u0000HI\u0005\u0005\u0000\u0000IK\u0005\u001a\u0000\u0000JL\u0003"+
		"\u000e\u0007\u0000KJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000"+
		"MK\u0001\u0000\u0000\u0000MN\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000"+
		"\u0000OP\u0005\u001b\u0000\u0000P\r\u0001\u0000\u0000\u0000QR\u0003\u0018"+
		"\f\u0000RS\u0005\"\u0000\u0000ST\u0005\u001c\u0000\u0000TY\u0003\u001a"+
		"\r\u0000UV\u0005\u0001\u0000\u0000VX\u0003\u001a\r\u0000WU\u0001\u0000"+
		"\u0000\u0000X[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001"+
		"\u0000\u0000\u0000Z\\\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000"+
		"\\]\u0005\u0016\u0000\u0000]\u000f\u0001\u0000\u0000\u0000^_\u0005\u0006"+
		"\u0000\u0000_c\u0005\u001a\u0000\u0000`b\u0003\u0012\t\u0000a`\u0001\u0000"+
		"\u0000\u0000be\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000\u0000cd\u0001"+
		"\u0000\u0000\u0000df\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000"+
		"fg\u0005\u001b\u0000\u0000g\u0011\u0001\u0000\u0000\u0000hi\u0003\u0018"+
		"\f\u0000ij\u0005\"\u0000\u0000jk\u0005\u001c\u0000\u0000kp\u0003\u001a"+
		"\r\u0000lm\u0005\u0001\u0000\u0000mo\u0003\u001a\r\u0000nl\u0001\u0000"+
		"\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000pq\u0001"+
		"\u0000\u0000\u0000qs\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000"+
		"st\u0005\u0016\u0000\u0000t\u0013\u0001\u0000\u0000\u0000uv\u0005\u0007"+
		"\u0000\u0000vw\u0005\u001a\u0000\u0000wx\u0003\u0016\u000b\u0000xy\u0005"+
		"\u001b\u0000\u0000yz\u0005\u0016\u0000\u0000z\u0015\u0001\u0000\u0000"+
		"\u0000{|\u0003\u001a\r\u0000|}\u0005\u0016\u0000\u0000}\u0017\u0001\u0000"+
		"\u0000\u0000~\u007f\u0005\"\u0000\u0000\u007f\u0019\u0001\u0000\u0000"+
		"\u0000\u0080\u0081\u0006\r\uffff\uffff\u0000\u0081\u0082\u0005\u0018\u0000"+
		"\u0000\u0082\u0083\u0003\u001a\r\u0000\u0083\u0084\u0005\u0019\u0000\u0000"+
		"\u0084\u0098\u0001\u0000\u0000\u0000\u0085\u0086\u0005\"\u0000\u0000\u0086"+
		"\u008f\u0005\u0018\u0000\u0000\u0087\u008c\u0003\u001a\r\u0000\u0088\u0089"+
		"\u0005\u0002\u0000\u0000\u0089\u008b\u0003\u001a\r\u0000\u008a\u0088\u0001"+
		"\u0000\u0000\u0000\u008b\u008e\u0001\u0000\u0000\u0000\u008c\u008a\u0001"+
		"\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u0090\u0001"+
		"\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000\u008f\u0087\u0001"+
		"\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0091\u0001"+
		"\u0000\u0000\u0000\u0091\u0098\u0005\u0019\u0000\u0000\u0092\u0093\u0005"+
		"\u0011\u0000\u0000\u0093\u0098\u0003\u001a\r\t\u0094\u0095\u0005\u0015"+
		"\u0000\u0000\u0095\u0098\u0003\u001a\r\b\u0096\u0098\u0003\u001c\u000e"+
		"\u0000\u0097\u0080\u0001\u0000\u0000\u0000\u0097\u0085\u0001\u0000\u0000"+
		"\u0000\u0097\u0092\u0001\u0000\u0000\u0000\u0097\u0094\u0001\u0000\u0000"+
		"\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0098\u00ad\u0001\u0000\u0000"+
		"\u0000\u0099\u009a\n\u0007\u0000\u0000\u009a\u009b\u0007\u0000\u0000\u0000"+
		"\u009b\u00ac\u0003\u001a\r\b\u009c\u009d\n\u0006\u0000\u0000\u009d\u009e"+
		"\u0007\u0001\u0000\u0000\u009e\u00ac\u0003\u001a\r\u0007\u009f\u00a0\n"+
		"\u0005\u0000\u0000\u00a0\u00a1\u0007\u0002\u0000\u0000\u00a1\u00ac\u0003"+
		"\u001a\r\u0006\u00a2\u00a3\n\u0004\u0000\u0000\u00a3\u00a4\u0007\u0003"+
		"\u0000\u0000\u00a4\u00ac\u0003\u001a\r\u0005\u00a5\u00a6\n\u0003\u0000"+
		"\u0000\u00a6\u00a7\u0005\t\u0000\u0000\u00a7\u00ac\u0003\u001a\r\u0004"+
		"\u00a8\u00a9\n\u0002\u0000\u0000\u00a9\u00aa\u0005\b\u0000\u0000\u00aa"+
		"\u00ac\u0003\u001a\r\u0003\u00ab\u0099\u0001\u0000\u0000\u0000\u00ab\u009c"+
		"\u0001\u0000\u0000\u0000\u00ab\u009f\u0001\u0000\u0000\u0000\u00ab\u00a2"+
		"\u0001\u0000\u0000\u0000\u00ab\u00a5\u0001\u0000\u0000\u0000\u00ab\u00a8"+
		"\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u001b"+
		"\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00ba"+
		"\u0005#\u0000\u0000\u00b1\u00ba\u0007\u0004\u0000\u0000\u00b2\u00ba\u0005"+
		"\"\u0000\u0000\u00b3\u00ba\u0005 \u0000\u0000\u00b4\u00ba\u0005!\u0000"+
		"\u0000\u00b5\u00b6\u0005!\u0000\u0000\u00b6\u00b7\u0005\u0018\u0000\u0000"+
		"\u00b7\u00b8\u0005#\u0000\u0000\u00b8\u00ba\u0005\u0019\u0000\u0000\u00b9"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b9\u00b1\u0001\u0000\u0000\u0000\u00b9"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b9\u00b3\u0001\u0000\u0000\u0000\u00b9"+
		"\u00b4\u0001\u0000\u0000\u0000\u00b9\u00b5\u0001\u0000\u0000\u0000\u00ba"+
		"\u001d\u0001\u0000\u0000\u0000\u000e&-:AMYcp\u008c\u008f\u0097\u00ab\u00ad"+
		"\u00b9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}