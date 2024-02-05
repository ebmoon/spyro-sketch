package spyro.synthesis.primitives;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;
import spyro.compiler.ast.grammar.RHSTerm;

import java.util.*;
import java.util.stream.Collectors;

public class MinimizationSketchBuilder extends CommonSketchBuilder {

    public final static String sizeVarID = "formula_size";
    public final static String retVarID = "out";

    public MinimizationSketchBuilder(Program impl) {
        super(impl);
    }

    public List<Statement> doRHSTermMinimize(RHSTerm t) {
        generatorCxt = new HashMap<>();
        stmtsToPrepend = new ArrayList<>();
        sketch.compiler.ast.core.exprs.Expression e = (sketch.compiler.ast.core.exprs.Expression) t.accept(this);

        List<sketch.compiler.ast.core.typs.Type> types = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();
        List<ExprFunCall> generatorCalls = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : generatorCxt.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            int numPrevNonterminals = maxCxt.getOrDefault(key, 0);
            if (numPrevNonterminals >= value)
                continue;

            maxCxt.put(key, value);     // This value is new maximum

            for (int i = numPrevNonterminals; i < value; i++) {
                String varID = String.format("var_%s_%d", key, i);
                String funID = String.format("%s_gen", key);

                List<sketch.compiler.ast.core.exprs.Expression> paramVars;
                paramVars = variableAsParams.stream()
                        .map(param -> new ExprVar((FENode) null, param.getName()))
                        .collect(Collectors.toList());
                paramVars.add(new ExprVar((FENode) null, sizeVarID));
                paramVars.add(new ExprVar((FENode) null, varID));

                names.add(varID);
                types.add(nonterminalToSketchType.get(key));
                inits.add(null);
                generatorCalls.add(new ExprFunCall((FENode) null, funID, paramVars));
            }
        }


        ExprVar sizeVar = new ExprVar((FENode) null, sizeVarID);
        ExprBinary sizeVarIncreased = new ExprBinary((FENode) null, ExprBinary.BINOP_ADD, sizeVar, ExprConstInt.createConstant(t.size()));
        stmtsToPrepend.add(new StmtAssign(sizeVar, sizeVarIncreased));

        StmtVarDecl stmtVarDecl = new StmtVarDecl((FENode) null, types, names, inits);
        ExprVar retVar = new ExprVar((FENode) null, retVarID);
        stmtsToPrepend.add(new StmtAssign((FENode) null, retVar, e));
        stmtsToPrepend.add(new StmtReturn((FENode) null, null));
        StmtBlock consBlock = new StmtBlock(stmtsToPrepend);
        StmtIfThen stmtIfThen = new StmtIfThen((FENode) null, new ExprStar(consBlock, 1), consBlock, null);

        List<Statement> stmts;
        if (!names.isEmpty()) {
            stmts = new ArrayList<>();
            stmts.add(stmtVarDecl);
            stmts.addAll(generatorCalls.stream().map(expr -> new StmtExpr((FENode) null, expr)).collect(Collectors.toList()));
            stmts.add(stmtIfThen);
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
                .flatMap(t -> doRHSTermMinimize(t).stream())
                .collect(Collectors.toList());
        // One of production rule must be chosen
        bodyStmts.add(new StmtAssert((FENode) null, new ExprConstInt(0), 0));
        Statement body = new StmtBlock((FENode) null, bodyStmts);

        List<Parameter> params = new ArrayList<>(variableAsParams);
        params.add(new Parameter((FENode) null, TypePrimitive.inttype, sizeVarID, Parameter.REF));
        params.add(new Parameter((FENode) null, returnType, retVarID, Parameter.REF));

        fc.params(params);
        fc.returnType(TypePrimitive.voidtype);
        fc.body(body);

        return fc.create();
    }
}
