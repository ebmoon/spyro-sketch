package spyro.compiler.parser;

import spyro.compiler.ast.Query;
import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.grammar.*;
import spyro.compiler.ast.type.Type;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;
import spyro.util.exceptions.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class provides an implementation of Spyro AST builder.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class BuildAstVisitor extends SpyroBaseVisitor<SpyroNode> {

    Map<String, Variable> varContextWithType;
    Map<String, RHSVariable> varContext;
    Map<String, Nonterminal> nonterminalContext;

    @Override
    public Query visitParse(SpyroParser.ParseContext ctx) {
        return visitProgram(ctx.program());
    }

    @Override
    public Query visitProgram(SpyroParser.ProgramContext ctx) {
        List<Variable> variables = ctx.declVariables().declVar().stream()
                .map(this::visitDeclVar)
                .collect(Collectors.toList());

        // id -> Variable map
        varContextWithType = variables.stream()
                .collect(Collectors.toMap(Variable::getID, Function.identity()));
        varContext = variables.stream()
                .collect(Collectors.toMap(Variable::getID, RHSVariable::new));

        List<ExprFuncCall> signatures = ctx.declSignatures().declSig().stream()
                .map(this::visitDeclSig)
                .collect(Collectors.toList());

        // TODO Handle anonymous functions
        List<GrammarRule> grammar = visitLanguage(ctx.declLanguage());

        List<ExampleRule> examples = visitExamples(ctx.declExamples());

        if(ctx.declAssumptions() == null)
            return new Query(variables, signatures, grammar, examples);

        List<ExprFuncCall> assumptions = ctx.declAssumptions().declAssumption().stream()
                .map(this::visitDeclAssumption)
                .collect(Collectors.toList());

        return new Query(variables, signatures, grammar, examples, assumptions);
    }

    public Variable visitDeclVar(SpyroParser.DeclVarContext ctx) {
        if (ctx instanceof SpyroParser.DeclVisibleVarContext)
            return visitDeclVisibleVar((SpyroParser.DeclVisibleVarContext) ctx);
        else if (ctx instanceof SpyroParser.DeclHiddenVarContext)
            return visitDeclHiddenVar((SpyroParser.DeclHiddenVarContext) ctx);
        else
            throw new ParseException("Unknown variable declaration case");
    }

    @Override
    public Variable visitDeclVisibleVar(SpyroParser.DeclVisibleVarContext ctx) {
        Type type = visitType(ctx.type());
        String id = ctx.ID().getText();
        SpyroParser.ExGenNoteContext exgen = ctx.exGenNote();

        if (exgen == null) {
            return new Variable(type, id);
        } else {
            return new Variable(type, id, exgen.ID().getText());
        }
    }

    @Override
    public Variable visitDeclHiddenVar(SpyroParser.DeclHiddenVarContext ctx) {
        Type type = visitType(ctx.type());
        String id = ctx.ID().getText();
        SpyroParser.ExGenNoteContext exgen = ctx.exGenNote();

        if (exgen == null) {
            return new Variable(type, id, true);
        } else {
            return new Variable(type, id, exgen.ID().getText(), true);
        }
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

    public ExprFuncCall visitSignature(SpyroParser.FunctionExprContext ctx) {
        String id = ctx.ID().getText();

        // It only allows variables as argument
        List<Expression> args = ctx.expr().stream()
                .map(exprCtx -> ((SpyroParser.AtomExprContext) exprCtx).atom())
                .map(atomCtx -> ((SpyroParser.IdAtomContext) atomCtx).ID().getText())
                .map(varID -> varContextWithType.get(varID))
                .collect(Collectors.toList());

        return new ExprFuncCall(id, args);
    }

    @Override
    public ExprFuncCall visitDeclSig(SpyroParser.DeclSigContext ctx) {
        SpyroParser.ExprContext expr = ctx.expr();
        if (expr instanceof SpyroParser.FunctionExprContext) {
            return visitSignature((SpyroParser.FunctionExprContext) expr);
        } else {
            throw new ParseException("Signature must be a form of function call");
        }
    }

    public RHSTerm visitExpression(SpyroParser.ExprContext ctx) {
        if (ctx instanceof SpyroParser.FunctionExprContext)
            return visitFunctionExpr((SpyroParser.FunctionExprContext) ctx);
        else if (ctx instanceof SpyroParser.ParenExprContext)
            return visitExpression(((SpyroParser.ParenExprContext) ctx).expr());
        else if (ctx instanceof SpyroParser.UnaryMinusExprContext)
            return visitUnaryMinusExpr((SpyroParser.UnaryMinusExprContext) ctx);
        else if (ctx instanceof SpyroParser.NotExprContext)
            return visitNotExpr((SpyroParser.NotExprContext) ctx);
        else if (ctx instanceof SpyroParser.MultiplicationExprContext)
            return visitMultiplicationExpr((SpyroParser.MultiplicationExprContext) ctx);
        else if (ctx instanceof SpyroParser.AdditiveExprContext)
            return visitAdditiveExpr((SpyroParser.AdditiveExprContext) ctx);
        else if (ctx instanceof SpyroParser.RelationalExprContext)
            return visitRelationalExpr((SpyroParser.RelationalExprContext) ctx);
        else if (ctx instanceof SpyroParser.EqualityExprContext)
            return visitEqualityExpr((SpyroParser.EqualityExprContext) ctx);
        else if (ctx instanceof SpyroParser.AndExprContext)
            return visitAndExpr((SpyroParser.AndExprContext) ctx);
        else if (ctx instanceof SpyroParser.OrExprContext)
            return visitOrExpr((SpyroParser.OrExprContext) ctx);
        else if (ctx instanceof SpyroParser.AtomExprContext)
            return visitAtom(((SpyroParser.AtomExprContext) ctx).atom());
        else if (ctx instanceof SpyroParser.AnonFuncExprContext)
            return visitAnonFuncExpr((SpyroParser.AnonFuncExprContext) ctx);
        else
            throw new ParseException("Unknown expression case");
    }

    public RHSTerm visitAtom(SpyroParser.AtomContext ctx) {
        if (ctx instanceof SpyroParser.NumberAtomContext)
            return visitNumberAtom((SpyroParser.NumberAtomContext) ctx);
        else if (ctx instanceof SpyroParser.BooleanAtomContext)
            return visitBooleanAtom((SpyroParser.BooleanAtomContext) ctx);
        else if (ctx instanceof SpyroParser.NullAtomContext)
            return visitNullAtom((SpyroParser.NullAtomContext) ctx);
        else if (ctx instanceof SpyroParser.UnsizedHoleAtomContext)
            return visitUnsizedHoleAtom((SpyroParser.UnsizedHoleAtomContext) ctx);
        else if (ctx instanceof SpyroParser.SizedHoleAtomContext)
            return visitSizedHoleAtom((SpyroParser.SizedHoleAtomContext) ctx);
        else if (ctx instanceof SpyroParser.IdAtomContext)
            return visitIdAtom((SpyroParser.IdAtomContext) ctx);
        else
            throw new ParseException("Unknown atom expression");
    }

    @Override
    public RHSConstInt visitNumberAtom(SpyroParser.NumberAtomContext ctx) {
        int val = Integer.parseInt(ctx.INT().getText());
        return new RHSConstInt(val);
    }

    @Override
    public RHSConstBool visitBooleanAtom(SpyroParser.BooleanAtomContext ctx) {
        if (ctx.TRUE() != null)
            return new RHSConstBool(true);
        else if (ctx.FALSE() != null)
            return new RHSConstBool(false);
        else
            throw new ParseException("Unknown boolean value");
    }

    @Override
    public RHSConstNull visitNullAtom(SpyroParser.NullAtomContext ctx) {
        return new RHSConstNull();
    }

    @Override
    public RHSHole visitUnsizedHoleAtom(SpyroParser.UnsizedHoleAtomContext ctx) {
        return new RHSHole();
    }

    @Override
    public RHSHole visitSizedHoleAtom(SpyroParser.SizedHoleAtomContext ctx) {
        int size = Integer.parseInt(ctx.INT().getText());
        return new RHSHole(size);
    }

    @Override
    public RHSTerm visitIdAtom(SpyroParser.IdAtomContext ctx) {
        String id = ctx.ID().getText();
        if (varContext.containsKey(id)) {
            return varContext.get(id);
        } else if (nonterminalContext.containsKey(id)) {
            return nonterminalContext.get(id);
        } else {
            throw new ParseException("Unknown variable " + id);
        }
    }

    @Override
    public RHSTerm visitAnonFuncExpr(SpyroParser.AnonFuncExprContext ctx) {
        String id = ctx.ID().getText();

        varContext.put(id, new RHSVariable(id));
        RHSTerm expr = visitExpression(ctx.expr());
        varContext.remove(id);

        return new RHSLambda(id, expr);
    }

    @Override
    public RHSFuncCall visitFunctionExpr(SpyroParser.FunctionExprContext ctx) {
        String id = ctx.ID().getText();
        List<RHSTerm> exprs = ctx.expr().stream()
                .map(this::visitExpression)
                .collect(Collectors.toList());

        return new RHSFuncCall(id, exprs);
    }

    @Override
    public RHSUnary visitUnaryMinusExpr(SpyroParser.UnaryMinusExprContext ctx) {
        RHSTerm expr = visitExpression(ctx.expr());
        return new RHSUnary(ExprUnary.UnaryOp.UNOP_NEG, expr);
    }

    @Override
    public RHSUnary visitNotExpr(SpyroParser.NotExprContext ctx) {
        RHSTerm expr = visitExpression(ctx.expr());
        return new RHSUnary(ExprUnary.UnaryOp.UNOP_NOT, expr);
    }

    @Override
    public RHSBinary visitMultiplicationExpr(SpyroParser.MultiplicationExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));
        ExprBinary.BinaryOp op;

        if (ctx.MULT() != null) {
            op = ExprBinary.BinaryOp.BINOP_MUL;
        } else if (ctx.DIV() != null) {
            op = ExprBinary.BinaryOp.BINOP_DIV;
        } else if (ctx.MOD() != null) {
            op = ExprBinary.BinaryOp.BINOP_MOD;
        } else {
            throw new ParseException("Unknown multiplicative binary operator");
        }

        return new RHSBinary(op, expr1, expr2);
    }

    @Override
    public RHSBinary visitAdditiveExpr(SpyroParser.AdditiveExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));
        ExprBinary.BinaryOp op;

        if (ctx.PLUS() != null) {
            op = ExprBinary.BinaryOp.BINOP_ADD;
        } else if (ctx.MINUS() != null) {
            op = ExprBinary.BinaryOp.BINOP_SUB;
        } else {
            throw new ParseException("Unknown additive binary operator");
        }

        return new RHSBinary(op, expr1, expr2);
    }

    @Override
    public RHSBinary visitRelationalExpr(SpyroParser.RelationalExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));
        ExprBinary.BinaryOp op;

        if (ctx.LTEQ() != null) {
            op = ExprBinary.BinaryOp.BINOP_LE;
        } else if (ctx.GTEQ() != null) {
            op = ExprBinary.BinaryOp.BINOP_GE;
        } else if (ctx.LT() != null) {
            op = ExprBinary.BinaryOp.BINOP_LT;
        } else if (ctx.GT() != null) {
            op = ExprBinary.BinaryOp.BINOP_GT;
        } else {
            throw new ParseException("Unknown relational binary operator");
        }

        return new RHSBinary(op, expr1, expr2);
    }

    @Override
    public RHSBinary visitEqualityExpr(SpyroParser.EqualityExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));
        ExprBinary.BinaryOp op;

        if (ctx.EQ() != null) {
            op = ExprBinary.BinaryOp.BINOP_EQ;
        } else if (ctx.NEQ() != null) {
            op = ExprBinary.BinaryOp.BINOP_NEQ;
        } else {
            throw new ParseException("Unknown equality binary operator");
        }

        return new RHSBinary(op, expr1, expr2);
    }

    @Override
    public RHSBinary visitAndExpr(SpyroParser.AndExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));

        return new RHSBinary(ExprBinary.BinaryOp.BINOP_AND, expr1, expr2);
    }

    @Override
    public RHSBinary visitOrExpr(SpyroParser.OrExprContext ctx) {
        RHSTerm expr1 = visitExpression(ctx.expr(0));
        RHSTerm expr2 = visitExpression(ctx.expr(1));

        return new RHSBinary(ExprBinary.BinaryOp.BINOP_OR, expr1, expr2);
    }

    public List<GrammarRule> visitLanguage(SpyroParser.DeclLanguageContext ctx) {
        List<SpyroParser.DeclLanguageRuleContext> ruleContexts = ctx.declLanguageRule();

        // Construct an context with nonterminals
        nonterminalContext = new HashMap<>();
        for (SpyroParser.DeclLanguageRuleContext ruleContext : ruleContexts) {
            String nonterminalID = ruleContext.ID().getText();
            Type ty = visitType(ruleContext.type());
            Nonterminal v = new Nonterminal(ty, nonterminalID);

            nonterminalContext.put(nonterminalID, v);
        }

        // Construct GrammarRule objects with extended context
        List<GrammarRule> rules = ruleContexts.stream()
                .map(this::visitDeclLanguageRule)
                .collect(Collectors.toList());

        return rules;
    }

    public List<ExampleRule> visitExamples(SpyroParser.DeclExamplesContext ctx) {
        List<SpyroParser.DeclExampleRuleContext> ruleContexts = ctx.declExampleRule();

        // Construct a context with nonterminals
        nonterminalContext = new HashMap<>();
        for (SpyroParser.DeclExampleRuleContext ruleContext : ruleContexts) {
            String nonterminalID = ruleContext.ID().getText();
            Type ty = visitType(ruleContext.type());
            Nonterminal v = new Nonterminal(ty, nonterminalID);

            nonterminalContext.put(nonterminalID, v);
        }

        // Construct ExampleRule objects with extended context
        List<ExampleRule> rules = ruleContexts.stream()
                .map(this::visitDeclExampleRule)
                .collect(Collectors.toList());

        return rules;
    }

    @Override
    public GrammarRule visitDeclLanguageRule(SpyroParser.DeclLanguageRuleContext ctx) {
        String nonterminalID = ctx.ID().getText();
        Nonterminal nonterminal = nonterminalContext.get(nonterminalID);
        List<RHSTerm> rules = ctx.expr().stream()
                .map(this::visitExpression)
                .collect(Collectors.toList());

        return new GrammarRule(nonterminal, rules);
    }


    @Override
    public ExampleRule visitDeclExampleRule(SpyroParser.DeclExampleRuleContext ctx) {
        String nonterminalID = ctx.ID().getText();
        Nonterminal nonterminal = nonterminalContext.get(nonterminalID);
        List<RHSTerm> rules = ctx.expr().stream()
                .map(this::visitExpression)
                .collect(Collectors.toList());

        return new ExampleRule(nonterminal, rules);
    }
    public ExprFuncCall visitDeclAssumption(SpyroParser.DeclAssumptionContext ctx) {
        SpyroParser.ExprContext expr = ctx.expr();
        if (expr instanceof SpyroParser.FunctionExprContext) {
            return visitSignature((SpyroParser.FunctionExprContext) expr);
        } else {
            throw new ParseException("Assumption must be given as a boolean function");
        }
    }
}
