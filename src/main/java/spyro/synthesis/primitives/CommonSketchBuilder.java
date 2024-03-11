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

    public final static String pkgName = "Spyro";

    public final static String equalityOperatorSuffix = "_equal";
    public final static String assumptionOutVarPrefix = "asp_out_";
    public final static String assumpitonConjunctionId = "asp_conj";

    public final boolean isUnderProblem;
    Program prog;
    Program impl;
    List<Variable> variables;

    Map<Integer, List<Expression>> varAsExprs;
    Map<Integer, List<Parameter>> varAsParams;
    List<Parameter> hiddenVarAsParamsRef;

    List<ExprFunCall> signatures;
    List<Statement> signatureAsStmts;
    List<ExprFunCall> assumptions;
    List<Statement> assumptionAsStmts;

    List<Function> propertyGenerators;
    List<Function> exampleGenerators;

    Set<String> variableSet;
    Set<String> outputVariableSet;
    Map<String, sketch.compiler.ast.core.typs.Type> variableToSketchType;
    Map<String, sketch.compiler.ast.core.typs.Type> nonterminalToSketchType;

    Map<String, Function> firstExampleGenerators; // map from Spyro type to its generator

    List<Statement> stmtsToPrepend;
    Map<String, Integer> generatorCxt;
    Map<String, Integer> maxCxt;
    int freshVarCount;
    int hiddenNum;

    public CommonSketchBuilder(Program impl, boolean isUnderProblem) {
        this.prog = Program.emptyProgram();
        this.impl = impl;
        this.isUnderProblem = isUnderProblem;
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

    public List<Statement> getAssumptionAsStmts() {
        if (assumptionAsStmts == null) {
            List<Statement> stmts = new ArrayList<>();
            List<Expression> exprs = new ArrayList<>();
            for (int i = 0, sz = assumptions.size(); i < sz; i++) {
                ExprFunCall asp = assumptions.get(i);
                String varId = assumptionOutVarPrefix + i;
                ExprVar var = new ExprVar((FENode) null, varId);
                stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, varId, null));

                List<Expression> params = new ArrayList<>(asp.getParams());
                params.add(var);
                stmts.add(new StmtExpr((FENode) null, new ExprFunCall((FENode) null, asp.getName(), params)));
                exprs.add(var);
//                stmts.add(new StmtAssume((FENode) null,
//                        new ExprVar((FENode) null, varId),
//                        "asp" + i));
            }

            Expression aspConj = ExprConstInt.one;
            if (!exprs.isEmpty()) {
                aspConj = exprs.get(0);
                for (int i = 1; i < exprs.size(); i++) {
                    aspConj = new sketch.compiler.ast.core.exprs.ExprBinary(sketch.compiler.ast.core.exprs.ExprBinary.BINOP_AND, aspConj, exprs.get(i));
                }
            }
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, assumpitonConjunctionId, aspConj));

            assumptionAsStmts = stmts;
        }
        return assumptionAsStmts;
    }

    public List<Function> getPropertyGenerators() {
        return propertyGenerators;
    }

    public List<Function> getExampleGenerators() {
        return exampleGenerators;
    }

    public Boolean isOutputVariable(String varId) {
        return outputVariableSet.contains(varId);
    }


    /**
     * Construct a series of declare statements for variables
     * (Comments: Now we assume output is always visible. Consider v/h and i/o as a 2-bit mask  )
     *
     * @param varIncluded 1101: only visible
     *                    1110: only hidden
     *                    0111: only input
     *                    1011: only output
     * @param withInit    0: without
     *                    1: with
     */
    public Statement getVariableDecls(int varIncluded, int withInit) {
        List<sketch.compiler.ast.core.typs.Type> types = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

        for (Variable v : variables) {
            int varKind = v.varKind();
            if ((varKind & varIncluded) != varKind)
                continue;
            String varID = v.getID();
            Type ty = v.getType();
            sketch.compiler.ast.core.typs.Type sketchType = doType(ty);

            names.add(varID);
            types.add(sketchType);
            if (withInit == W_INIT) {
                String funID = v.getGeneratorID();
                if (funID == null) {
                    Function generator = firstExampleGenerators.get(ty.toString());
                    if (generator == null)
                        throw new SketchConversionException(String.format("Missed generator for type %s.", ty.toString()));
                    funID = generator.getName();
                }
                inits.add(new ExprFunCall((FENode) null, funID, new ArrayList<>()));
            } else inits.add(null);
        }

        if (types.isEmpty())
            return new StmtEmpty((FENode) null);

        return new StmtVarDecl((FENode) null, types, names, inits);
    }

    /**
     * Returns a list of variables converted to expressions
     *
     * @param varIncluded 1101: only visible
     *                    1110: only hidden
     *                    0111: only input
     *                    1011: only output
     */
    public List<Expression> getVariableAsExprs(int varIncluded, String extendedOutputID) {
        List<Expression> exprs = varAsExprs.get(varIncluded);
        if (exprs == null) {
            exprs = new ArrayList<>();
            for (Variable v : variables) {
                int varKind = v.varKind();
                if ((varKind & varIncluded) != varKind)
                    continue;
                String varID = v.getID();
                exprs.add(new ExprVar((FENode) null, varID));
            }
            varAsExprs.put(varIncluded, exprs);
        }
        if (extendedOutputID != null) {
            exprs = new ArrayList<>(exprs);
            exprs.add(new ExprVar((FENode) null, extendedOutputID));
        }
        return exprs;
    }

    /**
     * Returns a list of variables converted to parameters
     *
     * @param varIncluded 1101: only visible
     *                    1110: only hidden
     *                    0111: only input
     *                    1011: only output
     */
    public List<Parameter> getVariableAsParams(int varIncluded, String extendedOutputID) {
        List<Parameter> params = varAsParams.get(varIncluded);
        if (params == null) {
            params = new ArrayList<>();
            for (Variable v : variables) {
                int h = v.isHidden() ? 1 : 0, o = v.isOutput() ? 1 : 0;
                int varKind = (1 - h) | (h << 1) | ((1 - o) << 2) | (o << 3);
                if ((varKind & varIncluded) != varKind)
                    continue;
                params.add(variableToParam(v, false));
            }
            varAsParams.put(varIncluded, params);
        }
        if (extendedOutputID != null) {
            params = new ArrayList<>(params);
            params.add(new Parameter((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, extendedOutputID, Parameter.REF));
        }
        return params;
    }

    public List<StmtAssign> getHiddenVariablesWithHole() {
        List<StmtAssign> stmts = new ArrayList<>();

        for (Variable v : variables)
            if (v.isHidden()) {
                String varID = v.getID();
                Type ty = v.getType();
                String funID = v.getGeneratorID();
                if (funID == null) {
                    Function generator = firstExampleGenerators.get(ty.toString());
                    if (generator == null)
                        throw new SketchConversionException(String.format("Missed generator for type %s.", ty.toString()));
                    funID = generator.getName();
                }
                stmts.add(new StmtAssign(new ExprVar((FENode) null, varID), new ExprFunCall((FENode) null, funID, new ArrayList<>())));
            }
        return stmts;
    }

    public List<Parameter> getHiddenVarAsParamsRef() {
        if (hiddenVarAsParamsRef == null) {
            hiddenVarAsParamsRef = variables.stream()
                    .filter(Variable::isHidden)
                    .map(v -> variableToParam(v, true))
                    .collect(Collectors.toList());
        }
        return hiddenVarAsParamsRef;
    }

    public boolean hasHidden() {
        return hiddenNum > 0;
    }

    protected Parameter variableToParam(Variable var, boolean isRef) {
        sketch.compiler.ast.core.typs.Type ty = (sketch.compiler.ast.core.typs.Type) var.getType().accept(this);
        return new Parameter((FENode) null, ty, var.getID(), isRef ? Parameter.REF : Parameter.IN);
    }

    protected sketch.compiler.ast.core.typs.Type doType(Type ty) {
        return (sketch.compiler.ast.core.typs.Type) ty.accept(this);
    }

    protected void setOutputVar(ExprFunCall sig) {
        for (Function func : getFunctions())
            if (func.getName().equals(sig.getName())) {
                List<Expression> args = sig.getParams();
                List<Parameter> params = func.getParams();
                int sz = args.size();
                if (sz != params.size())
                    throw new SketchConversionException("Number of parameters mismatch for " + sig.getName() + "().");
                for (int i = 0; i < sz; i++)
                    if(params.get(i).getPtype() == Parameter.REF)
                        outputVariableSet.add(args.get(i).toString());
                return ;
            }
        throw new SketchConversionException("Cannot find implementation for " + sig.getName() + "().");
    }

    @Override
    public Program visitQuery(Query q) {
        nonterminalToSketchType = new HashMap<>();
        firstExampleGenerators = new HashMap<>();
        freshVarCount = 0;
        outputVariableSet = new HashSet<>();
        varAsExprs = new HashMap<>();
        varAsParams = new HashMap<>();

        variables = q.getVariables();
        variableSet = variables.stream()
                .map(Variable::getID)
                .collect(Collectors.toSet());
        variableToSketchType = variables.stream()
                .collect(Collectors.toMap(Variable::getID, decl -> doType(decl.getType())));

        signatures = q.getSignatures().stream()
                .map(sig -> (ExprFunCall) sig.accept(this))
                .collect(Collectors.toList());
        signatureAsStmts = signatures.stream()
                .map(sig -> new StmtExpr((FENode) null, sig))
                .collect(Collectors.toList());

        assumptions = q.getAssumptions().stream()
                .map(asp -> (ExprFunCall) asp.accept(this))
                .collect(Collectors.toList());

        for (ExprFunCall sig : signatures)
            setOutputVar(sig);
        for (Variable var : variables)
            if (outputVariableSet.contains(var.getID())) var.setOutput();

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

        hiddenNum = getVariableAsExprs(ONLY_HIDDEN, null).size();

        return null;
    }

    private ExprVar freshVar() {
        return new ExprVar((FENode) null, String.format("fresh_var_%d", freshVarCount++));
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
        int size = hole.getSize();
        if (size > 0) {
            return new ExprStar(prog, size);
        } else {
            return new ExprStar(prog);
        }
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
        Function f = ResultExtractor.findFunction(impl, functionID);
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
        int size = hole.getSize();
        if (size > 0) {
            return new ExprStar(prog, size);
        } else {
            return new ExprStar(prog);
        }
    }

    @Override
    public ExprVar visitRHSVariable(RHSVariable v) {
        String varID = v.getID();
        if (variableSet.contains(varID)) {
            return new ExprVar((FENode) null, v.getID());
        } else
            throw new SketchConversionException("visitVariable called for a undefined variable");
    }

    @Override
    public ExprLambda visitRHSAnonFunc(RHSLambda lam) {
        List<ExprVar> params = new ArrayList<>();
        params.add(new ExprVar((FENode) null, lam.getParam()));

        Set<String> backup = new HashSet<>(variableSet);
        variableSet.add(lam.getParam());
        sketch.compiler.ast.core.exprs.Expression body =
                (sketch.compiler.ast.core.exprs.Expression) lam.getBody().accept(this);
        variableSet = backup;

        return new ExprLambda((FENode) null, params, body);
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

    public ExprFunCall doAssumption(ExprFunCall asp, int cnt) {
        List<Expression> params = new ArrayList<>(asp.getParams());
        params.add(new ExprVar((FENode) null, assumptionOutVarPrefix + cnt));
        return new ExprFunCall((FENode) null, asp.getName(), params);
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

            // Update max value
            maxCxt.put(key, value);

            for (int i = numPrevNonterminals; i < value; i++) {
                String varID = String.format("var_%s_%d", key, i);
                String funID = String.format("%s_gen", key);

                List<sketch.compiler.ast.core.exprs.Expression> paramVars;
                if (termType == RHSTermType.RHS_GRAMMAR)
                    paramVars = getVariableAsExprs(ONLY_VISIBLE, null);
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

        sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FENode) null, generatorID, Function.FcnType.Generator);
        List<Statement> bodyStmts = rule.getRules().stream()
                .flatMap(t -> doRHSTerm(t, RHSTermType.RHS_GRAMMAR).stream())
                .collect(Collectors.toList());
        // One of production rule must be chosen
        bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
        Statement body = new StmtBlock((FENode) null, bodyStmts);

        fc.params(getVariableAsParams(ONLY_VISIBLE, null));
        fc.returnType(returnType);
        fc.body(body);

        return fc.create();
    }

    @Override
    public sketch.compiler.ast.core.Function visitExampleRule(ExampleRule rule) {
        String nonterminalID = rule.getNonterminal().getID();
        String generatorID = String.format("%s_gen", nonterminalID);
        sketch.compiler.ast.core.typs.Type returnType = nonterminalToSketchType.get(nonterminalID);

        maxCxt = new HashMap<>();

        sketch.compiler.ast.core.Function.FunctionCreator fc = sketch.compiler.ast.core.Function.creator((FENode) null, generatorID, Function.FcnType.Generator);
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

        if (!firstExampleGenerators.containsKey(rule.getNonterminal().getType().toString()))
            firstExampleGenerators.put(rule.getNonterminal().getType().toString(), f);

        return f;
    }

    public enum RHSTermType {
        RHS_EXAMPLE, RHS_GRAMMAR
    }

    public static final int ALL_VAR = 15;
    public static final int ONLY_VISIBLE = 1 | 12;
    public static final int ONLY_HIDDEN = 2 | 12;
    public static final int ONLY_INPUT = 4 | 3;
    public static final int ONLY_OUTPUT = 8 | 3;

    public static final int WO_INIT = 0;
    public static final int W_INIT = 1;
}
