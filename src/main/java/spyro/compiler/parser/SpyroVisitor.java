// Generated from Spyro.g4 by ANTLR 4.13.1

package spyro.compiler.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SpyroParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SpyroVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SpyroParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(SpyroParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SpyroParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declVariables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclVariables(SpyroParser.DeclVariablesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declVisibleVar}
	 * labeled alternative in {@link SpyroParser#declVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclVisibleVar(SpyroParser.DeclVisibleVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code declHiddenVar}
	 * labeled alternative in {@link SpyroParser#declVar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclHiddenVar(SpyroParser.DeclHiddenVarContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declSignatures}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclSignatures(SpyroParser.DeclSignaturesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declSig}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclSig(SpyroParser.DeclSigContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declLanguage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclLanguage(SpyroParser.DeclLanguageContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declLanguageRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclLanguageRule(SpyroParser.DeclLanguageRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declExamples}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclExamples(SpyroParser.DeclExamplesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declExampleRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclExampleRule(SpyroParser.DeclExampleRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declAssumptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclAssumptions(SpyroParser.DeclAssumptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#declAssumption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclAssumption(SpyroParser.DeclAssumptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SpyroParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SpyroParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(SpyroParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(SpyroParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicationExpr(SpyroParser.MultiplicationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(SpyroParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpr(SpyroParser.OrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(SpyroParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(SpyroParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(SpyroParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(SpyroParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionExpr(SpyroParser.FunctionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link SpyroParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(SpyroParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAtom(SpyroParser.NumberAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanAtom(SpyroParser.BooleanAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(SpyroParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullAtom(SpyroParser.NullAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unsizedHoleAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnsizedHoleAtom(SpyroParser.UnsizedHoleAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sizedHoleAtom}
	 * labeled alternative in {@link SpyroParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSizedHoleAtom(SpyroParser.SizedHoleAtomContext ctx);
}