package spyro.synthesis.primitives;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.TypeStructRef;
import spyro.compiler.ast.Query;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.ExprBinary;
import spyro.compiler.ast.expr.ExprUnary;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.grammar.*;
import spyro.compiler.ast.type.Type;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;
import spyro.util.exceptions.SketchConversionException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Common part to build sketch AST for each primitive query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class CommonSketchBuilder implements SpyroNodeVisitor {

    Program prog;
    Program impl;
    public final static String pkgName = "Spyro";

    List<Variable> variables;
    List<Parameter> variableAsParams;
    List<sketch.compiler.ast.core.exprs.Expression> variableAsExprs;

    List<ExprFunCall> signatures;
    List<Statement> signatureAsStmts;

    List<Function> propertyGenerators;
    List<Function> exampleGenerators;

    Map<String, sketch.compiler.ast.core.typs.Type> variableToSketchType;
    Map<String, sketch.compiler.ast.core.typs.Type> nonterminalToSketchType;
    Map<String, Function> firstExampleGenerators;

    List<Statement> stmtsToPrepend;
    Map<String, Integer> generatorCxt;
    Map<String, Integer> maxCxt;
    int cnt;

    public CommonSketchBuilder(Program impl) {
        this.prog = Program.emptyProgram();
        this.impl = impl;
    }

    public List<StructDef> getStructDefinitions() {
        return impl.getPackages().get(0).getStructs();
    }

    public List<Function> getFunctions() {
        return impl.getPackages().get(0).getFuncs();
    }

    public List<ExprFunCall> getSignatures() {
        return signatures;
    }

    public List<Statement> getSignatureAsStmts() {
        return signatureAsStmts;
    }

    public List<Function> getPropertyGenerators() {
        return propertyGenerators;
    }

    public List<Function> getExampleGenerators() {
        return exampleGenerators;
    }

    public List<sketch.compiler.ast.core.exprs.Expression> getVariableAsExprs() {
        return variableAsExprs;
    }

    public List<Parameter> getVariableAsParams() {
        return variableAsParams;
    }

    public List<Parameter> getExtendedParams(String outputVarID) {
        Parameter outputParam = new Parameter((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, outputVarID, Parameter.REF);
        List<Parameter> extendedParams = new ArrayList<>(variableAsParams);
        extendedParams.add(outputParam);

        return extendedParams;
    }

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
            String varID = v.getID();
            Type ty = v.getType();
            sketch.compiler.ast.core.typs.Type sketchType = doType(ty);

            names.add(varID);
            types.add(sketchType);
            String funId = firstExampleGenerators.get(ty.toString()).getName();
            inits.add(new ExprFunCall((FENode) null, funId, new ArrayList<>()));
        }

        return new StmtVarDecl((FENode) null, types, names, inits);
    }

    protected Parameter variableToParam(Variable var) {
        sketch.compiler.ast.core.typs.Type ty = (sketch.compiler.ast.core.typs.Type) var.getType().accept(this);
        return new Parameter((FENode) null, ty, var.getID());
    }

    protected sketch.compiler.ast.core.typs.Type doType(Type ty) {
        return (sketch.compiler.ast.core.typs.Type) ty.accept(this);
    }

    @Override
    public Program visitQuery(Query q) {
        nonterminalToSketchType = new HashMap<>();
        firstExampleGenerators = new HashMap<>();
        cnt = 0;

        variables = q.getVariables();
        variableAsExprs = variables.stream()
                .map(decl -> new ExprVar((FENode) null, decl.getID()))
                .collect(Collectors.toList());
        variableAsParams = variables.stream()
                .map(this::variableToParam)
                .collect(Collectors.toList());
        variableToSketchType = variables.stream()
                .collect(Collectors.toMap(Variable::getID, decl -> doType(decl.getType())));

        signatures = q.getSignatures().stream()
                .map(sig -> (ExprFunCall) sig.accept(this))
                .collect(Collectors.toList());
        signatureAsStmts = signatures.stream()
                .map(sig -> new StmtExpr((FENode) null, sig))
                .collect(Collectors.toList());

        nonterminalToSketchType = q.getGrammar().stream()
                .map(GrammarRule::getNonterminal)
                .collect(Collectors.toMap(Nonterminal::getID, n -> this.doType(n.getType())));
        propertyGenerators = q.getGrammar().stream()
                .map(rule -> (Function) rule.accept(this))
                .collect(Collectors.toList());

        nonterminalToSketchType = q.getExamples().stream()
                .map(ExampleRule::getNonterminal)
                .collect(Collectors.toMap(Nonterminal::getID, n -> this.doType(n.getType())));
        exampleGenerators = q.getExamples().stream()
                .map(rule -> (Function) rule.accept(this))
                .collect(Collectors.toList());

        return null;
    }

    private ExprVar freshVar() {
        return new ExprVar((FENode) null, String.format("fresh_var_%d", cnt++));
    }

    @Override
    public sketch.compiler.ast.core.exprs.Expression visitVariable(Variable v) {
        String varID = v.getID();
        if (variableToSketchType.containsKey(varID)) {
            return new ExprVar((FENode) null, v.getID());
        } else
            throw new SketchConversionException("visitVariable called for a undefined variable");
    }

    @Override
    public ExprStar visitHole(Hole hole) {
        return new ExprStar(prog, hole.getSize());
    }

    @Override
    public ExprFunCall visitExprFuncCall(ExprFuncCall fc) {
        List<sketch.compiler.ast.core.exprs.Expression> params = fc.getArgs().stream()
                .map(e -> (sketch.compiler.ast.core.exprs.Expression) e.accept(this))
                .collect(Collectors.toList());

        return new ExprFunCall((FENode) null, fc.getFunctionID(), params);
    }

    @Override
    public sketch.compiler.ast.core.exprs.ExprUnary visitExprUnary(ExprUnary e) {
        Expression subExpr = (Expression) e.getExpr().accept(this);
        return constructExprUnary(subExpr, e.getOp());
    }

    @Override
    public sketch.compiler.ast.core.exprs.ExprBinary visitExprBinary(ExprBinary e) {
        return constructExprBinary((Expression) e.getLeft().accept(this), (Expression) e.getRight().accept(this), e.getOp());
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
    public Object visitConstNull(ConstNull nullptr) {
        return ExprNullPtr.nullPtr;
    }

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

    @Override
    public Object visitNonterminal(Nonterminal n) {
        String varID = n.getID();
        if (nonterminalToSketchType.containsKey(varID)) {
            int cnt = generatorCxt.getOrDefault(varID, 0);
            String varId = String.format("var_%s_%d", n.getID(), cnt);
            generatorCxt.put(varID, cnt + 1);
            return new ExprVar((FENode) null, varId);
        } else
            throw new SketchConversionException("visitVariable called for a undefined variable");
    }

    @Override
    public Object visitRHSUnary(RHSUnary e) {
        Expression subExpr = (Expression) e.getExpr().accept(this);
        return constructExprUnary(subExpr, e.getOp());
    }

    private sketch.compiler.ast.core.exprs.ExprUnary constructExprUnary(Expression subExpr, ExprUnary.UnaryOp op) {
        switch (op) {
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
    public sketch.compiler.ast.core.exprs.ExprBinary visitRHSBinary(RHSBinary e) {
        Expression left = (Expression) e.getLeft().accept(this);
        Expression right = (Expression) e.getRight().accept(this);
        return constructExprBinary(left, right, e.getOp());
    }

    private sketch.compiler.ast.core.exprs.ExprBinary constructExprBinary(Expression left, Expression right, ExprBinary.BinaryOp op) {
        switch (op) {
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

    private sketch.compiler.ast.core.typs.Type getReturnType(String functionID) {
        Function f = CommonResultExtractor.findFunction(impl, functionID);
        if (f == null)
            throw new SketchConversionException("Unknown function " + functionID);

        List<Parameter> params = f.getParams();
        return params.get(params.size() - 1).getType();
    }

    @Override
    public Object visitRHSFuncCall(RHSFuncCall fc) {
        ExprVar fresh = freshVar();
        sketch.compiler.ast.core.typs.Type ty = getReturnType(fc.getFunctionID());

        List<sketch.compiler.ast.core.exprs.Expression> params = fc.getArgs().stream()
                .map(e -> (sketch.compiler.ast.core.exprs.Expression) e.accept(this))
                .collect(Collectors.toList());
        params.add(fresh);

        stmtsToPrepend.add(new StmtVarDecl((FENode) null, ty, fresh.getName(), null));
        stmtsToPrepend.add(new StmtExpr(new ExprFunCall((FENode) null, fc.getFunctionID(), params)));

        return fresh;
    }

    @Override
    public ExprStar visitRHSHole(RHSHole hole) {
        return new ExprStar(prog, hole.getSize());
    }

    @Override
    public ExprVar visitRHSVariable(RHSVariable v) {
        String varID = v.getID();
        if (variableToSketchType.containsKey(varID)) {
            return new ExprVar((FENode) null, v.getID());
        } else
            throw new SketchConversionException("visitVariable called for a undefined variable");
    }

    @Override
    public ExprConstInt visitRHSConstBool(RHSConstBool b) {
        return b.getValue() ? ExprConstInt.one : ExprConstInt.zero;
    }

    @Override
    public ExprConstInt visitRHSConstInt(RHSConstInt n) {
        return ExprConstInt.createConstant(n.getValue());
    }

    @Override
    public ExprNullPtr visitRHSConstNull(RHSConstNull nullptr) {
        return ExprNullPtr.nullPtr;
    }

    public List<Statement> doRHSTerm(RHSTerm t, RHSTermType termType) {
        generatorCxt = new HashMap<>();
        stmtsToPrepend = new ArrayList<>();
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

            maxCxt.put(key, value);     // This value is new maxmmum

            for (int i = numPrevNonterminals; i < value; i++) {
                String varID = String.format("var_%s_%d", key, i);
                String funID = String.format("%s_gen", key);

                List<sketch.compiler.ast.core.exprs.Expression> paramVars;
                if (termType == RHSTermType.RHS_GRAMMAR)
                    paramVars = variableAsParams.stream()
                            .map(param -> new ExprVar((FENode) null, param.getName()))
                            .collect(Collectors.toList());
                else
                    paramVars = new ArrayList<>();


                names.add(varID);
                types.add(nonterminalToSketchType.get(key));
                inits.add(new ExprFunCall((FENode) null, funID, paramVars));
            }
        }

        StmtVarDecl stmtVarDecl = new StmtVarDecl((FENode) null, types, names, inits);
        stmtsToPrepend.add(new StmtReturn((FENode) null, e));
        StmtBlock consBlock = new StmtBlock(stmtsToPrepend);
        StmtIfThen stmtIfThen = new StmtIfThen((FENode) null, new ExprStar(consBlock, 1), consBlock, null);

        List<Statement> stmts;
        if (!names.isEmpty()) {
            stmts = Arrays.asList(stmtVarDecl, stmtIfThen);
        } else {
            stmts = Collections.singletonList(stmtIfThen);
        }

        return stmts;
    }

    @Override
    public Object visitGrammarRule(GrammarRule rule) {
        String nonterminalID = rule.getNonterminal().getID();
        String generatorID = String.format("%s_gen", nonterminalID);
        sketch.compiler.ast.core.typs.Type returnType = nonterminalToSketchType.get(nonterminalID);

        maxCxt = new HashMap<>();

        sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FEContext) null, generatorID, Function.FcnType.Generator);

        List<Statement> bodyStmts = rule.getRules().stream()
                .flatMap(t -> doRHSTerm(t, RHSTermType.RHS_GRAMMAR).stream())
                .collect(Collectors.toList());
        // One of production rule must be chosen
        bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
        Statement body = new StmtBlock((FENode) null, bodyStmts);

        fc.params(variableAsParams);
        fc.returnType(returnType);
        fc.body(body);

        return fc.create();
    }

    @Override
    public sketch.compiler.ast.core.Function visitExampleRule(ExampleRule rule) {
        String nonterminalID = rule.getNonterminal().getID();
        String generatorID = String.format("%s_gen", nonterminalID);
        sketch.compiler.ast.core.typs.Type returnType = nonterminalToSketchType.get(nonterminalID);

        sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FEContext) null, generatorID, Function.FcnType.Generator);
        List<Statement> bodyStmts = rule.getRules().stream()
                .flatMap(t -> doRHSTerm(t, RHSTermType.RHS_EXAMPLE).stream())
                .collect(Collectors.toList());
        // One of production rule must be chosen
        bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
        Statement body = new StmtBlock((FENode) null, bodyStmts);

        fc.params(new ArrayList<>());
        fc.returnType(returnType);
        fc.body(body);

        Function f = fc.create();

        if (!firstExampleGenerators.containsKey(returnType.toString()))
            firstExampleGenerators.put(returnType.toString(), f);

        return f;
    }

    public enum RHSTermType {
        RHS_EXAMPLE, RHS_GRAMMAR
    }
}
