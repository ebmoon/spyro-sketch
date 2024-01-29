package spyro.synthesizer;

import sketch.compiler.ast.core.FEContext;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprStar;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.Type;
import sketch.compiler.ast.core.typs.TypeStructRef;
import spyro.compiler.ast.Query;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;
import spyro.util.exceptions.SketchConversionException;

import java.util.*;
import java.util.stream.Collectors;


public class GeneratorBuilder implements SpyroNodeVisitor {

    List<Function> typeGenerators;
    List<Function> nonTerminalGenerators;
    Function propertyGen;
    List<Parameter> variableAsParams;
    List<sketch.compiler.ast.core.exprs.Expression> variableAsExprs;
//    List<Parameter> variableAsParamsWithOut;
//    List<sketch.compiler.ast.core.exprs.Expression> variableAsExprsWithOut;
    List<ExprFunCall> signatures;
    HashMap<Variable, Type> variableSketchType; // map from Spyro variable to its Sketch type
    HashMap<Variable, Type> nonTerminalSketchType; // map from Spyro Nonterminal to its Sketch type
    HashMap<Variable, Integer> generatorCxt;

    public GeneratorBuilder() {
    }

    public List<Function> getTypeGenerators() {
        return typeGenerators;
    }

    public List<Function> getNonTerminalGenerators() {
        return nonTerminalGenerators;
    }

    public Function getPropertyGen() {
        return propertyGen;
    }

    public HashMap<Variable, Type> getVariableSketchType() {
        return variableSketchType;
    }

    public List<ExprFunCall> getSignatures() {
        return signatures;
    }

    public List<Parameter> getVariableAsParams() {
        return variableAsParams;
    }

    public List<sketch.compiler.ast.core.exprs.Expression> getVariableAsExprs() {
        return variableAsExprs;
    }

    public List<sketch.compiler.ast.core.exprs.Expression> appendToVariableAsExprs(String id) {
        List<sketch.compiler.ast.core.exprs.Expression> ret = new ArrayList<>(variableAsExprs);
        ret.add(new ExprVar((FENode) null, id));
        return ret;
    }

    public List<Parameter> appendToVariableAsParams(String id) {
        List<Parameter> ret = new ArrayList<>(variableAsParams);
        ret.add(new Parameter(null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, id, Parameter.REF));
        return ret;
    }


    private Parameter variableToParam(Variable var) {
        Type ty = (Type) var.getType().accept(this);

        return new Parameter((FENode) null, ty, var.getId());
    }

    @Override
    public Object visitQuery(Query q) {
        typeGenerators = new ArrayList<>();
        nonTerminalGenerators = new ArrayList<>();
        variableAsParams = new ArrayList<>();
        nonTerminalSketchType = new HashMap<>();
        variableSketchType = new HashMap<>();
        variableAsExprs = new ArrayList<>();
        signatures = new ArrayList<>();

        for (Variable decl : q.getVariables()) {
            Type ty = (Type) decl.getType().accept(this);
            variableSketchType.put(decl, ty);

            variableAsExprs.add(new ExprVar((FENode) null, decl.getId()));
            variableAsParams.add(variableToParam(decl));
        }

//        variableAsExprsWithOut = new ArrayList<>(variableAsExprs);
//        variableAsExprsWithOut.add(new ExprVar((FENode) null, "out"));
//        variableAsParamsWithOut = new ArrayList<>(variableAsParams);
//        variableAsParamsWithOut.add(new Parameter(null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, "out", Parameter.REF));


        for (ExprFuncCall sig : q.getSignatures()) {
            ExprFunCall fun = (ExprFunCall) sig.accept(this);
            signatures.add(fun);
        }

        for (ExampleRule rule : q.getExamples()) {
            Function gen = (Function) rule.accept(this);
            typeGenerators.add(gen);
        }

        for (GrammarRule rule : q.getGrammar()) {
            Variable nt = rule.getNonterminal();
            Type ty = (Type) nt.getType().accept(this);
            nonTerminalSketchType.put(nt, ty);
        }

        for (GrammarRule rule : q.getGrammar()) {
            Function gen = (Function) rule.accept(this);
            nonTerminalGenerators.add(gen);
        }
        return null;
    }

    @Override
    public sketch.compiler.ast.core.exprs.Expression visitVariable(Variable v) {
        if (nonTerminalSketchType.containsKey(v)) {
//            String generatorID = String.format(
//                    "%s_gen",
//                    v.getId()
//            );
//
//            List<sketch.compiler.ast.core.exprs.Expression> paramVars = generatorParams.stream()
//                    .map(param -> new ExprVar((FENode) null, param.getName()))
//                    .collect(Collectors.toList());
//
//            return new ExprFunCall((FENode) null, generatorID, paramVars);
            int cnt = generatorCxt.getOrDefault(v, 0);
            String varId = String.format("var_%s_%d", v.getId(), cnt);
            generatorCxt.put(v, cnt + 1);
            return new ExprVar((FENode) null, varId);

        } else if (variableSketchType.containsKey(v)) {
            return new ExprVar((FENode) null, v.getId());
        } else
            throw new SketchConversionException("visitVariable called for a undefined variable");
    }

    @Override
    public ExprStar visitHole(Hole hole) {
        return new ExprStar((FEContext) null, hole.getSize());
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
    public Object visitConstNull(ConstNull nullptr) {
        return null;
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

    public Statement doRHSTerm(Expression t) {
        generatorCxt = new HashMap<>();
        sketch.compiler.ast.core.exprs.Expression e = (sketch.compiler.ast.core.exprs.Expression) t.accept(this);

        List<Type> types = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

        for (Map.Entry<Variable, Integer> entry : generatorCxt.entrySet()) {
            Variable key = entry.getKey();
            int value = entry.getValue();

//            int numPrevNonterminals = maxCxt.getOrDefault(key, 0);
//            if (numPrevNonterminals >= value)
//                continue;

            for (int i = 0; i < value; i++) {
                String varID = String.format("var_%s_%d", key.getId(), i);
                String funID = String.format(
                        "%s_gen",
                        key.getId()
                );

                List<sketch.compiler.ast.core.exprs.Expression> paramVars = variableAsParams.stream()
                        .map(param -> new ExprVar((FENode) null, param.getName()))
                        .collect(Collectors.toList());

                names.add(varID);
                types.add(nonTerminalSketchType.get(key));
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
        String generatorID = String.format("%s_gen", rule.getNonterminal().getId());

        Type returnType = nonTerminalSketchType.get(rule.getNonterminal());

        Function.FunctionCreator fc = Function.creator((FEContext) null, generatorID, Function.FcnType.Generator);

        List<Statement> rhs = rule.getRules().stream()
                .map(this::doRHSTerm)
                .collect(Collectors.toList());
        Statement body = new StmtBlock((FENode) null, rhs);

        fc.params(variableAsParams);
        fc.returnType(returnType);
        fc.body(body);

        return fc.create();
    }


    @Override
    public Function visitExampleRule(ExampleRule rule) {
        String generatorID = String.format("%s_gen", rule.getType().toString());

        Type returnType = (Type) rule.getType().accept(this);

        Function.FunctionCreator fc = Function.creator((FEContext) null, generatorID, Function.FcnType.Generator);
        List<Statement> rhs = rule.getRules().stream()
                .map(rhsTerm -> (sketch.compiler.ast.core.exprs.Expression) rhsTerm.accept(this))
                .map(expr -> new StmtReturn((FENode) null, expr))
                .map(stmt -> new StmtIfThen((FENode) null, new ExprStar(stmt), stmt, null)) // Xuanyu: Check the parameters for ExprStar()
                .collect(Collectors.toList());
        Statement body = new StmtBlock((FENode) null, rhs);

        fc.params(new ArrayList<>());
        fc.returnType(returnType);
        fc.body(body);

        return fc.create();
    }


}
