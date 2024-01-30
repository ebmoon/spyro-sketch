package spyro.synthesis.primitives;

import java.util.*;
import java.util.stream.Collectors;

import antlr.Grammar;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.TypeStructRef;
import spyro.compiler.ast.Query;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.expr.ExprBinary;
import spyro.compiler.ast.expr.ExprUnary;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;
import spyro.compiler.ast.type.Type;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;
import spyro.util.exceptions.SketchConversionException;

/**
 * Common part to build sketch AST for each primitive query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class CommonSketchBuilder implements SpyroNodeVisitor {

	Program impl;

	List<Variable> variables;
	List<Parameter> variableAsParams;
	List<sketch.compiler.ast.core.exprs.Expression> variableAsExprs;

	List<ExprFunCall> signatures;
	List<Statement> signatureAsStmts;

	List<Function> propertyGenerators;
	List<Function> exampleGenerators;

	Map<String, sketch.compiler.ast.core.typs.Type> variableToSketchType;
	Map<String, sketch.compiler.ast.core.typs.Type> nonterminalToSketchType;
	Map<String, sketch.compiler.ast.core.typs.Type> typeStringToSketchType;
	Map<String, Integer> generatorCxt;
	Map<String, Integer> maxCxt;

	public CommonSketchBuilder(Program impl) { this.impl = impl; }
	public List<StructDef> getStructDefinitions() { return impl.getPackages().get(0).getStructs(); }
	public List<Function> getFunctions() { return impl.getPackages().get(0).getFuncs(); }
	public List<ExprFunCall> getSignatures() { return signatures; }
	public List<Statement> getSignatureAsStmts() { return signatureAsStmts; }
	public List<Function> getPropertyGenerators() { return propertyGenerators; }
	public List<Function> getExampleGenerators() { return exampleGenerators; };

	public List<sketch.compiler.ast.core.exprs.Expression> appendToVariableAsExprs(ExprVar v) {
		List<sketch.compiler.ast.core.exprs.Expression> ret = new ArrayList<>(variableAsExprs);
		ret.add(v);
		return ret;
	}

	public StmtVarDecl getVariablesWithHole() {
		List<sketch.compiler.ast.core.typs.Type> types = new ArrayList<>();
		List<String> names = new ArrayList<>();
		List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

		for (Variable v : variables) {
			String varID = v.getId();
			Type ty = v.getType();
			sketch.compiler.ast.core.typs.Type sketchType = doType(ty);

			names.add(varID);
			types.add(sketchType);
			String funId = String.format("%s_gen", ty.toString());
			inits.add(new ExprFunCall((FENode) null, funId, new ArrayList<>()));
		}

		return new StmtVarDecl((FENode) null, types, names, inits);
	}

	private Parameter variableToParam(Variable var) {
		sketch.compiler.ast.core.typs.Type ty = (sketch.compiler.ast.core.typs.Type) var.getType().accept(this);
		return new Parameter((FENode) null, ty, var.getId());
	}

	private sketch.compiler.ast.core.typs.Type doType(Type ty) {
		return (sketch.compiler.ast.core.typs.Type) ty.accept(this);
	}

	@Override
	public Object visitQuery(Query q) {
		nonterminalToSketchType = new HashMap<>();

		variables = q.getVariables();
		variableAsExprs = variables.stream()
				.map(decl -> new ExprVar((FENode) null, decl.getId()))
				.collect(Collectors.toList());
		variableAsParams = variables.stream()
				.map(this::variableToParam)
				.collect(Collectors.toList());
		variableToSketchType = variables.stream()
				.collect(Collectors.toMap(Variable::getId, decl -> doType(decl.getType())));

		signatures = q.getSignatures().stream()
				.map(sig -> (ExprFunCall) sig.accept(this))
				.collect(Collectors.toList());
		signatureAsStmts = signatures.stream()
				.map(sig -> new StmtExpr((FENode) null, sig))
				.collect(Collectors.toList());

		nonterminalToSketchType = q.getGrammar().stream()
				.map(GrammarRule::getNonterminal)
				.collect(Collectors.toMap(Variable::getId, v -> this.doType(v.getType())));

		propertyGenerators = q.getGrammar().stream()
				.map(rule -> (Function) rule.accept(this))
				.collect(Collectors.toList());
		exampleGenerators = q.getExamples().stream()
				.map(rule -> (Function) rule.accept(this))
				.collect(Collectors.toList());

		return null;
	}

	@Override
	public sketch.compiler.ast.core.exprs.Expression visitVariable(Variable v) {
		String varID = v.getId();
		if (nonterminalToSketchType.containsKey(varID)) {
			int cnt = generatorCxt.getOrDefault(varID, 0);
			String varId = String.format("var_%s_%d", v.getId(), cnt);
			generatorCxt.put(varID, cnt + 1);
			return new ExprVar((FENode) null, varId);
		} else if (typeStringToSketchType.containsKey(varID)) {
			int cnt = generatorCxt.getOrDefault(varID, 0);
			String varId = String.format("var_%s_%d", v.getId(), cnt);
			generatorCxt.put(varID, cnt + 1);
			return new ExprVar((FENode) null, varId);
		} else if (variableToSketchType.containsKey(varID)) {
			return new ExprVar((FENode) null, v.getId());
		} else
			throw new SketchConversionException("visitVariable called for a undefined variable");
	}

	@Override
	public ExprStar visitHole(Hole hole) {
		return new ExprStar((FENode) null, hole.getSize());
	}

	@Override
	public Object visitExprFuncCall(ExprFuncCall fc) {
		List<sketch.compiler.ast.core.exprs.Expression> params = fc.getArgs().stream()
				.map(e -> (sketch.compiler.ast.core.exprs.Expression) e.accept(this))
				.collect(Collectors.toList());

		return new ExprFunCall((FENode) null, fc.getFunctionId(), params);
	}

	@Override
	public Object visitExprUnary(ExprUnary e) {
		sketch.compiler.ast.core.exprs.Expression subExpr = (sketch.compiler.ast.core.exprs.Expression) e.getExpr().accept(this);

		switch (e.getOp()) {
			case UNOP_NOT:
				return new sketch.compiler.ast.core.exprs.ExprUnary((FENode) null, sketch.compiler.ast.core.exprs.ExprUnary.UNOP_NOT, subExpr);
			case UNOP_BNOT:
				return new sketch.compiler.ast.core.exprs.ExprUnary((FENode) null, sketch.compiler.ast.core.exprs.ExprUnary.UNOP_BNOT, subExpr);
			case UNOP_NEG:
				return new sketch.compiler.ast.core.exprs.ExprUnary((FENode) null, sketch.compiler.ast.core.exprs.ExprUnary.UNOP_NEG, subExpr);
			default:
				throw new SketchConversionException("Unknown unary operator");
		}
	}

	@Override
	public sketch.compiler.ast.core.exprs.ExprBinary visitExprBinary(ExprBinary e) {
		sketch.compiler.ast.core.exprs.Expression left = (sketch.compiler.ast.core.exprs.Expression) e.getLeft().accept(this);
		sketch.compiler.ast.core.exprs.Expression right = (sketch.compiler.ast.core.exprs.Expression) e.getRight().accept(this);
		switch (e.getOp()) {
			// Arithmetic
			case BINOP_ADD:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_ADD, left, right);
			case BINOP_SUB:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_SUB, left, right);
			case BINOP_MUL:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_MUL, left, right);
			case BINOP_DIV:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_DIV, left, right);
			case BINOP_MOD:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_MOD, left, right);
			// Boolean
			case BINOP_AND:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_AND, left, right);
			case BINOP_OR:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_OR, left, right);
			// Comparison
			case BINOP_EQ:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_EQ, left, right);
			case BINOP_NEQ:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_NEQ, left, right);
			case BINOP_LT:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_LT, left, right);
			case BINOP_LE:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_LE, left, right);
			case BINOP_GT:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_GT, left, right);
			case BINOP_GE:
				return new sketch.compiler.ast.core.exprs.ExprBinary((FENode) null, sketch.compiler.ast.core.exprs.ExprBinary.BINOP_GE, left, right);
			default:
				throw new SketchConversionException("Unknown binary operator");
		}
	}

	@Override
	public ExprConstInt visitConstInt(ConstInt n) {
		return ExprConstInt.createConstant(n.getValue());
	}

	@Override
	public ExprConstInt visitConstBool(ConstBool b) {
		return b.getValue() ? ExprConstInt.one : ExprConstInt.zero;
	}

	@Override
	public Object visitConstNull(ConstNull nullptr) { return ExprNullPtr.nullPtr; }

	@Override
	public sketch.compiler.ast.core.typs.TypePrimitive visitTypePrimitive(TypePrimitive type) {
		switch (type.getPredefinedType()) {
			case TYPE_INT:
				return sketch.compiler.ast.core.typs.TypePrimitive.inttype;
			case TYPE_BOOLEAN:
				return sketch.compiler.ast.core.typs.TypePrimitive.bittype;
			default:
				throw new SketchConversionException("Unknown predefined type");
		}
	}

	@Override
	public Object visitTypeStruct(TypeStruct type) {
		return new TypeStructRef(type.toString(), false, null);
	}

	public Statement doGrammarRHSTerm(Expression t) {
		generatorCxt = new HashMap<>();
		sketch.compiler.ast.core.exprs.Expression e = (sketch.compiler.ast.core.exprs.Expression) t.accept(this);

		List<sketch.compiler.ast.core.typs.Type> types = new ArrayList<>();
		List<String> names = new ArrayList<>();
		List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : generatorCxt.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();

			int numPrevNonterminals = maxCxt.getOrDefault(key, 0);
			if (numPrevNonterminals >= value)
				continue;

			for (int i = 0; i < value; i++) {
				String varID = String.format("var_%s_%d", key, i);
				String funID = String.format("%s_gen", key);

				List<sketch.compiler.ast.core.exprs.Expression> paramVars = variableAsParams.stream()
						.map(param -> new ExprVar((FENode) null, param.getName()))
						.collect(Collectors.toList());

				names.add(varID);
				types.add(nonterminalToSketchType.get(key));
				inits.add(new ExprFunCall((FENode) null, funID, paramVars));
			}
		}

		StmtVarDecl stmtVarDecl = new StmtVarDecl((FENode) null, types, names, inits);
		StmtReturn stmtReturn = new StmtReturn((FENode) null, e);
		StmtIfThen stmtIfThen = new StmtIfThen((FENode) null, new ExprStar(stmtReturn), stmtReturn, null);

		StmtBlock stmt;
		if (!names.isEmpty()) {
			stmt = new StmtBlock((FENode) null, Arrays.asList(stmtVarDecl, stmtIfThen));
		} else {
			stmt = new StmtBlock((FENode) null, Collections.singletonList(stmtIfThen));
		}

		return stmt;
	}

	@Override
	public Object visitGrammarRule(GrammarRule rule) {
		String nonterminalID = rule.getNonterminal().getId();
		String generatorID = String.format("%s_gen", nonterminalID);
		sketch.compiler.ast.core.typs.Type returnType = nonterminalToSketchType.get(nonterminalID);

		maxCxt = new HashMap<>();

		sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FEContext) null, generatorID, sketch.compiler.ast.core.Function.FcnType.Generator);

		List<Statement> bodyStmts = rule.getRules().stream()
				.map(this::doGrammarRHSTerm)
				.collect(Collectors.toList());
		// One of production rule must be chosen
		bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
		Statement body = new StmtBlock((FENode) null, bodyStmts);

		fc.params(variableAsParams);
		fc.returnType(returnType);
		fc.body(body);

		return fc.create();
	}


	public Statement doExampleRHSTerm(Expression t) {
		generatorCxt = new HashMap<>();
		sketch.compiler.ast.core.exprs.Expression e = (sketch.compiler.ast.core.exprs.Expression) t.accept(this);

		List<sketch.compiler.ast.core.typs.Type> types = new ArrayList<>();
		List<String> names = new ArrayList<>();
		List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : generatorCxt.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();

			int numPrevNonterminals = maxCxt.getOrDefault(key, 0);
			if (numPrevNonterminals >= value)
				continue;

			for (int i = 0; i < value; i++) {
				String varID = String.format("var_%s_%d", key, i);
				String funID = String.format("%s_gen", key);

				List<sketch.compiler.ast.core.exprs.Expression> paramVars = variableAsParams.stream()
						.map(param -> new ExprVar((FENode) null, param.getName()))
						.collect(Collectors.toList());

				names.add(varID);
				types.add(nonterminalToSketchType.get(key));
				inits.add(new ExprFunCall((FENode) null, funID, paramVars));
			}
		}

		StmtVarDecl stmtVarDecl = new StmtVarDecl((FENode) null, types, names, inits);
		StmtReturn stmtReturn = new StmtReturn((FENode) null, e);
		StmtIfThen stmtIfThen = new StmtIfThen((FENode) null, new ExprStar(stmtReturn), stmtReturn, null);

		StmtBlock stmt;
		if (!names.isEmpty()) {
			stmt = new StmtBlock((FENode) null, Arrays.asList(stmtVarDecl, stmtIfThen));
		} else {
			stmt = new StmtBlock((FENode) null, Collections.singletonList(stmtIfThen));
		}

		return stmt;
	}

	@Override
	public sketch.compiler.ast.core.Function visitExampleRule(ExampleRule rule) {
		String generatorID = String.format("%s_gen", rule.getType().toString());
		sketch.compiler.ast.core.typs.Type returnType = doType(rule.getType());

		sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FEContext) null, generatorID, Function.FcnType.Generator);
		List<Statement> bodyStmts = rule.getRules().stream()
				.map(this::doExampleRHSTerm)
				.collect(Collectors.toList());
		// One of production rule must be chosen
		bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
		Statement body = new StmtBlock((FENode) null, bodyStmts);

		fc.params(new ArrayList<>());
		fc.returnType(returnType);
		fc.body(body);

		return fc.create();
	}
}
