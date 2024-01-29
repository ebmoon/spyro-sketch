package spyro.synthesis.primitives;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtVarDecl;
import spyro.compiler.ast.Query;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.ConstBool;
import spyro.compiler.ast.expr.ConstInt;
import spyro.compiler.ast.expr.ConstNull;
import spyro.compiler.ast.expr.ExprBinary;
import spyro.compiler.ast.expr.ExprFuncCall;
import spyro.compiler.ast.expr.ExprUnary;
import spyro.compiler.ast.expr.Hole;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;
import spyro.compiler.ast.type.Type;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;
import spyro.util.exceptions.SketchConversionException;

public class CommonSketchBuilder implements SpyroNodeVisitor {
	
	protected Program prog;
	
	// TODO 
	// Use two context for all variables (or all visible variables) and
	// all hidden variables
	protected Map<String, Variable> varContext;
	
	public CommonSketchBuilder(Program prog) { 
		this.prog = prog;
	}
	
	protected StmtVarDecl variableDecl() {
		List<String> ids = varContext.keySet().stream()
				.collect(Collectors.toList());
		
		// Start from ids to make sure ids and types have the same order
		List<sketch.compiler.ast.core.typs.Type> types = ids.stream()
				.map(id -> varContext.get(id))
				.map(v -> visitType(v.getType()))
				.collect(Collectors.toList());
		
		// TODO Update to example generator
		List<sketch.compiler.ast.core.exprs.Expression> inits = ids.stream()
				.map(id -> (sketch.compiler.ast.core.exprs.Expression) null)
				.collect(Collectors.toList());
		
		return new StmtVarDecl(prog, types, ids, inits);
	}
	
	protected Statement variableDecls() {
		return null;
	}
	
	@Override
	public Object visitQuery(Query q) {
		varContext = q.getVariables().stream()
				.collect(Collectors.toMap(Variable::getId, Function.identity()));
		
		return null;
	}

	@Override
	public Object visitVariable(Variable decl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitHole(Hole hole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExprFuncCall(ExprFuncCall fc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExprUnary(ExprUnary e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExprBinary(ExprBinary e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitConstInt(ConstInt n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitConstBool(ConstBool b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitConstNull(ConstNull nullptr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public sketch.compiler.ast.core.typs.TypePrimitive 
		visitTypePrimitive(TypePrimitive type) {
		TypePrimitive.PredefinedType ty = type.getPredefinedType();
		if (ty == TypePrimitive.PredefinedType.TYPE_INT) {
			return sketch.compiler.ast.core.typs.TypePrimitive.inttype;
		} else if (ty == TypePrimitive.PredefinedType.TYPE_BOOLEAN) {
			return sketch.compiler.ast.core.typs.TypePrimitive.bittype;
		} else {
			throw new SketchConversionException("Unknown primitive type");
		}
	}

	public sketch.compiler.ast.core.typs.Type visitType(Type type) {
		if (type instanceof TypePrimitive) {
			return visitTypePrimitive((TypePrimitive) type);
		} else if (type instanceof TypeStruct) {
			return visitTypeStruct((TypeStruct) type);
		} else {
			throw new SketchConversionException("Unknown type case");
		}
	}
	
	@Override
	public sketch.compiler.ast.core.typs.TypeStructRef 
		visitTypeStruct(TypeStruct type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitGrammarRule(GrammarRule rule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitExampleRule(ExampleRule rule) {
		// TODO Auto-generated method stub
		return null;
	}

}
