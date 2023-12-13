package spyro.compiler.parser;

import java.util.List;
import java.util.stream.Collectors;

import spyro.compiler.ast.*;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.type.*;

/**
 * This class provides an implementation of Spyro AST builder.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class BuildAstVisitor extends SpyroBaseVisitor<SpyroNode> {

	@Override 
	public Query visitParse(SpyroParser.ParseContext ctx) { 
		return visitProgram(ctx.program()); 
	}

	@Override 
	public Query visitProgram(SpyroParser.ProgramContext ctx) { 
		List<Variable> variables = ctx.declVariables().declVar().stream()
			.map(varCtx -> visitDeclVar(varCtx))
			.collect(Collectors.toList());
		
		// Not implemented
		List<ExprFuncCall> signatures = null;

		return new Query(variables, signatures);
	}

	public Variable visitDeclVar(SpyroParser.DeclVarContext ctx) {
		if (ctx instanceof SpyroParser.DeclVisibleVarContext)
			return visitDeclVisibleVar((SpyroParser.DeclVisibleVarContext) ctx);
		else if (ctx instanceof SpyroParser.DeclHiddenVarContext)
			return visitDeclHiddenVar((SpyroParser.DeclHiddenVarContext) ctx);
		return null;
	}
	
	@Override 
	public Variable visitDeclVisibleVar(SpyroParser.DeclVisibleVarContext ctx) { 
		Type type = visitType(ctx.type());
		String id = ctx.ID().getText();
		
		return new Variable(type, id); 
	}
	
	@Override 
	public Variable visitDeclHiddenVar(SpyroParser.DeclHiddenVarContext ctx) { 
		Type type = visitType(ctx.type());
		String id = ctx.ID().getText();
		
		return new Variable(type, id, true); 
	}
	
	@Override
	public Type visitType(SpyroParser.TypeContext ctx) {
		String id = ctx.ID().getText();
		if (Type.isPrimitiveId(id)) {
			return new TypePrimitive(id);
		} else {
			return new TypeStruct(id);
		}
	}
}
