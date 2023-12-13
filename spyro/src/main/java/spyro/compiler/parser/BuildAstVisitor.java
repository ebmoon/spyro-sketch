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

	@Override 
	public Variable visitDeclVar(SpyroParser.DeclVarContext ctx) { 
		Type type = visitType(ctx.type());
		
		return null; 
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
