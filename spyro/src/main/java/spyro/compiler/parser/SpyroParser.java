// Generated from Spyro.g4 by ANTLR 4.12.0

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
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, VARIABLES=3, SIGNATURES=4, LANGUAGE=5, EXAMPLES=6, ASSUMPTIONS=7, 
		OR=8, AND=9, EQ=10, NEQ=11, GT=12, LT=13, GTEQ=14, LTEQ=15, PLUS=16, MINUS=17, 
		MULT=18, DIV=19, MOD=20, NOT=21, SEMI=22, ASSIGN=23, LPAREN=24, RPAREN=25, 
		LBRACE=26, RBRACE=27, ARROW=28, TRUE=29, FALSE=30, NULL=31, HOLE=32, ID=33, 
		INT=34, FLOAT=35, STRING=36, COMMENT=37, SPACE=38, OTHER=39;
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
			null, "'|'", "','", "'variable'", "'signatures'", "'language'", "'examples'", 
			"'assumptions'", "'||'", "'&&'", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "';'", "'='", "'('", 
			"')'", "'{'", "'}'", "'->'", "'true'", "'false'", "'null'", "'??'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "VARIABLES", "SIGNATURES", "LANGUAGE", "EXAMPLES", 
			"ASSUMPTIONS", "OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", 
			"PLUS", "MINUS", "MULT", "DIV", "MOD", "NOT", "SEMI", "ASSIGN", "LPAREN", 
			"RPAREN", "LBRACE", "RBRACE", "ARROW", "TRUE", "FALSE", "NULL", "HOLE", 
			"ID", "INT", "FLOAT", "STRING", "COMMENT", "SPACE", "OTHER"
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
			} while ( _la==ID );
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
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(SpyroParser.ID, 0); }
		public TerminalNode SEMI() { return getToken(SpyroParser.SEMI, 0); }
		public DeclVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declVar; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SpyroVisitor ) return ((SpyroVisitor<? extends T>)visitor).visitDeclVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclVarContext declVar() throws RecognitionException {
		DeclVarContext _localctx = new DeclVarContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			type();
			setState(50);
			match(ID);
			setState(51);
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
			setState(53);
			match(SIGNATURES);
			setState(54);
			match(LBRACE);
			setState(56); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(55);
				declSig();
				}
				}
				setState(58); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 33841872896L) != 0) );
			setState(60);
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
			setState(62);
			expr(0);
			setState(63);
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
			setState(65);
			match(LANGUAGE);
			setState(66);
			match(LBRACE);
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(67);
				declLanguageRule();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			setState(72);
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
			setState(74);
			type();
			setState(75);
			match(ID);
			setState(76);
			match(ARROW);
			{
			setState(77);
			expr(0);
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(78);
				match(T__0);
				setState(79);
				expr(0);
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(85);
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
			setState(87);
			match(EXAMPLES);
			setState(88);
			match(LBRACE);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(89);
				declExampleRule();
				}
				}
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(95);
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
		public TerminalNode ARROW() { return getToken(SpyroParser.ARROW, 0); }
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
			setState(97);
			type();
			setState(98);
			match(ARROW);
			{
			setState(99);
			expr(0);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(100);
				match(T__0);
				setState(101);
				expr(0);
				}
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
			setState(107);
			match(ASSUMPTIONS);
			setState(108);
			match(LBRACE);
			setState(109);
			declAssumption();
			setState(110);
			match(RBRACE);
			setState(111);
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
			setState(113);
			expr(0);
			setState(114);
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
			setState(116);
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
			setState(141);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(119);
				match(LPAREN);
				setState(120);
				expr(0);
				setState(121);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new FunctionExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				match(ID);
				setState(124);
				match(LPAREN);
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 33841872896L) != 0)) {
					{
					setState(125);
					expr(0);
					setState(130);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(126);
						match(T__1);
						setState(127);
						expr(0);
						}
						}
						setState(132);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(135);
				match(RPAREN);
				}
				break;
			case 3:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(136);
				match(MINUS);
				setState(137);
				expr(9);
				}
				break;
			case 4:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(138);
				match(NOT);
				setState(139);
				expr(8);
				}
				break;
			case 5:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(140);
				atom();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(163);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(161);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(143);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(144);
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
						setState(145);
						expr(8);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(146);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(147);
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
						setState(148);
						expr(7);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(149);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(150);
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
						setState(151);
						expr(6);
						}
						break;
					case 4:
						{
						_localctx = new EqualityExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(152);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(153);
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
						setState(154);
						expr(5);
						}
						break;
					case 5:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(155);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(156);
						match(AND);
						setState(157);
						expr(4);
						}
						break;
					case 6:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(158);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(159);
						match(OR);
						setState(160);
						expr(3);
						}
						break;
					}
					} 
				}
				setState(165);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new NumberAtomContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(166);
				match(INT);
				}
				break;
			case 2:
				_localctx = new BooleanAtomContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(167);
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
				setState(168);
				match(ID);
				}
				break;
			case 4:
				_localctx = new NullAtomContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(169);
				match(NULL);
				}
				break;
			case 5:
				_localctx = new UnsizedHoleAtomContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(170);
				match(HOLE);
				}
				break;
			case 6:
				_localctx = new SizedHoleAtomContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(171);
				match(HOLE);
				setState(172);
				match(LPAREN);
				setState(173);
				match(INT);
				setState(174);
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
		"\u0004\u0001\'\u00b2\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001\'\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0004\u0002"+
		",\b\u0002\u000b\u0002\f\u0002-\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0004"+
		"\u00049\b\u0004\u000b\u0004\f\u0004:\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0004\u0006"+
		"E\b\u0006\u000b\u0006\f\u0006F\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007Q\b"+
		"\u0007\n\u0007\f\u0007T\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0005\b[\b\b\n\b\f\b^\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\tg\b\t\n\t\f\tj\t\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0005\r\u0081\b\r\n\r\f\r\u0084\t\r\u0003\r\u0086\b\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u008e\b\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00a2"+
		"\b\r\n\r\f\r\u00a5\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e"+
		"\u00b0\b\u000e\u0001\u000e\u0000\u0001\u001a\u000f\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0005\u0001"+
		"\u0000\u0012\u0014\u0001\u0000\u0010\u0011\u0001\u0000\f\u000f\u0001\u0000"+
		"\n\u000b\u0001\u0000\u001d\u001e\u00ba\u0000\u001e\u0001\u0000\u0000\u0000"+
		"\u0002!\u0001\u0000\u0000\u0000\u0004(\u0001\u0000\u0000\u0000\u00061"+
		"\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000\u0000\n>\u0001\u0000\u0000"+
		"\u0000\fA\u0001\u0000\u0000\u0000\u000eJ\u0001\u0000\u0000\u0000\u0010"+
		"W\u0001\u0000\u0000\u0000\u0012a\u0001\u0000\u0000\u0000\u0014k\u0001"+
		"\u0000\u0000\u0000\u0016q\u0001\u0000\u0000\u0000\u0018t\u0001\u0000\u0000"+
		"\u0000\u001a\u008d\u0001\u0000\u0000\u0000\u001c\u00af\u0001\u0000\u0000"+
		"\u0000\u001e\u001f\u0003\u0002\u0001\u0000\u001f \u0005\u0000\u0000\u0001"+
		" \u0001\u0001\u0000\u0000\u0000!\"\u0003\u0004\u0002\u0000\"#\u0003\b"+
		"\u0004\u0000#$\u0003\f\u0006\u0000$&\u0003\u0010\b\u0000%\'\u0003\u0014"+
		"\n\u0000&%\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000\u0000\'\u0003"+
		"\u0001\u0000\u0000\u0000()\u0005\u0003\u0000\u0000)+\u0005\u001a\u0000"+
		"\u0000*,\u0003\u0006\u0003\u0000+*\u0001\u0000\u0000\u0000,-\u0001\u0000"+
		"\u0000\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000./\u0001"+
		"\u0000\u0000\u0000/0\u0005\u001b\u0000\u00000\u0005\u0001\u0000\u0000"+
		"\u000012\u0003\u0018\f\u000023\u0005!\u0000\u000034\u0005\u0016\u0000"+
		"\u00004\u0007\u0001\u0000\u0000\u000056\u0005\u0004\u0000\u000068\u0005"+
		"\u001a\u0000\u000079\u0003\n\u0005\u000087\u0001\u0000\u0000\u00009:\u0001"+
		"\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000"+
		";<\u0001\u0000\u0000\u0000<=\u0005\u001b\u0000\u0000=\t\u0001\u0000\u0000"+
		"\u0000>?\u0003\u001a\r\u0000?@\u0005\u0016\u0000\u0000@\u000b\u0001\u0000"+
		"\u0000\u0000AB\u0005\u0005\u0000\u0000BD\u0005\u001a\u0000\u0000CE\u0003"+
		"\u000e\u0007\u0000DC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000"+
		"FD\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000"+
		"\u0000HI\u0005\u001b\u0000\u0000I\r\u0001\u0000\u0000\u0000JK\u0003\u0018"+
		"\f\u0000KL\u0005!\u0000\u0000LM\u0005\u001c\u0000\u0000MR\u0003\u001a"+
		"\r\u0000NO\u0005\u0001\u0000\u0000OQ\u0003\u001a\r\u0000PN\u0001\u0000"+
		"\u0000\u0000QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001"+
		"\u0000\u0000\u0000SU\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"UV\u0005\u0016\u0000\u0000V\u000f\u0001\u0000\u0000\u0000WX\u0005\u0006"+
		"\u0000\u0000X\\\u0005\u001a\u0000\u0000Y[\u0003\u0012\t\u0000ZY\u0001"+
		"\u0000\u0000\u0000[^\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000"+
		"\\]\u0001\u0000\u0000\u0000]_\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000"+
		"\u0000_`\u0005\u001b\u0000\u0000`\u0011\u0001\u0000\u0000\u0000ab\u0003"+
		"\u0018\f\u0000bc\u0005\u001c\u0000\u0000ch\u0003\u001a\r\u0000de\u0005"+
		"\u0001\u0000\u0000eg\u0003\u001a\r\u0000fd\u0001\u0000\u0000\u0000gj\u0001"+
		"\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000"+
		"i\u0013\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000kl\u0005\u0007"+
		"\u0000\u0000lm\u0005\u001a\u0000\u0000mn\u0003\u0016\u000b\u0000no\u0005"+
		"\u001b\u0000\u0000op\u0005\u0016\u0000\u0000p\u0015\u0001\u0000\u0000"+
		"\u0000qr\u0003\u001a\r\u0000rs\u0005\u0016\u0000\u0000s\u0017\u0001\u0000"+
		"\u0000\u0000tu\u0005!\u0000\u0000u\u0019\u0001\u0000\u0000\u0000vw\u0006"+
		"\r\uffff\uffff\u0000wx\u0005\u0018\u0000\u0000xy\u0003\u001a\r\u0000y"+
		"z\u0005\u0019\u0000\u0000z\u008e\u0001\u0000\u0000\u0000{|\u0005!\u0000"+
		"\u0000|\u0085\u0005\u0018\u0000\u0000}\u0082\u0003\u001a\r\u0000~\u007f"+
		"\u0005\u0002\u0000\u0000\u007f\u0081\u0003\u001a\r\u0000\u0080~\u0001"+
		"\u0000\u0000\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0086\u0001"+
		"\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085}\u0001\u0000"+
		"\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"+
		"\u0000\u0000\u0087\u008e\u0005\u0019\u0000\u0000\u0088\u0089\u0005\u0011"+
		"\u0000\u0000\u0089\u008e\u0003\u001a\r\t\u008a\u008b\u0005\u0015\u0000"+
		"\u0000\u008b\u008e\u0003\u001a\r\b\u008c\u008e\u0003\u001c\u000e\u0000"+
		"\u008dv\u0001\u0000\u0000\u0000\u008d{\u0001\u0000\u0000\u0000\u008d\u0088"+
		"\u0001\u0000\u0000\u0000\u008d\u008a\u0001\u0000\u0000\u0000\u008d\u008c"+
		"\u0001\u0000\u0000\u0000\u008e\u00a3\u0001\u0000\u0000\u0000\u008f\u0090"+
		"\n\u0007\u0000\u0000\u0090\u0091\u0007\u0000\u0000\u0000\u0091\u00a2\u0003"+
		"\u001a\r\b\u0092\u0093\n\u0006\u0000\u0000\u0093\u0094\u0007\u0001\u0000"+
		"\u0000\u0094\u00a2\u0003\u001a\r\u0007\u0095\u0096\n\u0005\u0000\u0000"+
		"\u0096\u0097\u0007\u0002\u0000\u0000\u0097\u00a2\u0003\u001a\r\u0006\u0098"+
		"\u0099\n\u0004\u0000\u0000\u0099\u009a\u0007\u0003\u0000\u0000\u009a\u00a2"+
		"\u0003\u001a\r\u0005\u009b\u009c\n\u0003\u0000\u0000\u009c\u009d\u0005"+
		"\t\u0000\u0000\u009d\u00a2\u0003\u001a\r\u0004\u009e\u009f\n\u0002\u0000"+
		"\u0000\u009f\u00a0\u0005\b\u0000\u0000\u00a0\u00a2\u0003\u001a\r\u0003"+
		"\u00a1\u008f\u0001\u0000\u0000\u0000\u00a1\u0092\u0001\u0000\u0000\u0000"+
		"\u00a1\u0095\u0001\u0000\u0000\u0000\u00a1\u0098\u0001\u0000\u0000\u0000"+
		"\u00a1\u009b\u0001\u0000\u0000\u0000\u00a1\u009e\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a5\u0001\u0000\u0000\u0000\u00a3\u00a1\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u001b\u0001\u0000\u0000\u0000"+
		"\u00a5\u00a3\u0001\u0000\u0000\u0000\u00a6\u00b0\u0005\"\u0000\u0000\u00a7"+
		"\u00b0\u0007\u0004\u0000\u0000\u00a8\u00b0\u0005!\u0000\u0000\u00a9\u00b0"+
		"\u0005\u001f\u0000\u0000\u00aa\u00b0\u0005 \u0000\u0000\u00ab\u00ac\u0005"+
		" \u0000\u0000\u00ac\u00ad\u0005\u0018\u0000\u0000\u00ad\u00ae\u0005\""+
		"\u0000\u0000\u00ae\u00b0\u0005\u0019\u0000\u0000\u00af\u00a6\u0001\u0000"+
		"\u0000\u0000\u00af\u00a7\u0001\u0000\u0000\u0000\u00af\u00a8\u0001\u0000"+
		"\u0000\u0000\u00af\u00a9\u0001\u0000\u0000\u0000\u00af\u00aa\u0001\u0000"+
		"\u0000\u0000\u00af\u00ab\u0001\u0000\u0000\u0000\u00b0\u001d\u0001\u0000"+
		"\u0000\u0000\r&-:FR\\h\u0082\u0085\u008d\u00a1\u00a3\u00af";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}