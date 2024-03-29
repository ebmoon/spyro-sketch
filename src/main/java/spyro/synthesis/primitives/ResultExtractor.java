package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;
import spyro.synthesis.HiddenValue;
import spyro.synthesis.Property;
import spyro.util.exceptions.SketchConversionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to parse synthesis result of each call to sketch
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ResultExtractor {

    public final static String suffixOfSynthResult = "_of_the_example";

    // For debug
    void enumerateStatements(List<Statement> stmts) {
        int idx = 0;
        for (Statement stmt : stmts) {
            System.out.println(idx++);
            System.out.println(stmt.getClass());
            System.out.println(stmt.toString());
        }
    }

    public static Function findFunction(Program prog, String name) {
        List<Function> funcs = prog.getPackages().get(0).getFuncs();
        for (Function func : funcs)
            if (func.getName().equals(name))
                return func;

        return null;
    }

    public static Property extractProperty(Program result) {
        Function prop = findFunction(result, Property.newPhiID);
        return new Property(prop);
    }


    public static Example extractPositiveExample(Program result) {
        StmtBlock body = (StmtBlock) findFunction(result, SoundnessOverSketchBuilder.soundnessFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        List<Statement> posExBody = new ArrayList<>(stmts.subList(0, numStmts - 3));

        final String tempVarID = "out";
        ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);
        params.add(new ExprVar((FENode) null, tempVarID));

        ExprFunCall newFunCall = new ExprFunCall((FENode) null, Property.newPhiID, params);

        posExBody.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, tempVarID, null));
        posExBody.add(new StmtExpr(newFunCall));
        posExBody.add(new StmtAssert(body, tempVar, false));

        return new Example(new StmtBlock(posExBody));
    }

    public static Example extractNegativeExamplePrecision(Program result) {
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionOverSketchBuilder.precisionFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        List<Statement> negExBody = new ArrayList<>(stmts.subList(0, numStmts - 9));

        final String tempVarID = "out";
        ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);
        params.add(new ExprVar((FENode) null, tempVarID));

        ExprFunCall newFunCall = new ExprFunCall((FENode) null, Property.newPhiID, params);

        negExBody.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, tempVarID, null));
        negExBody.add(new StmtExpr(newFunCall));
        negExBody.add(new StmtAssert(body, new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, tempVar), false));

        return new Example(new StmtBlock(negExBody));
    }

    public static Example extractNegativeExampleImprovement(Program result) {
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionOverSketchBuilder.precisionFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        List<Statement> negExBody = new ArrayList<>(stmts.subList(0, numStmts - 6));

        final String tempVarID = "out";
        ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);
        params.add(new ExprVar((FENode) null, tempVarID));

        ExprFunCall newFunCall = new ExprFunCall((FENode) null, Property.newPhiID, params);

        negExBody.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, tempVarID, null));
        negExBody.add(new StmtExpr(newFunCall));
        negExBody.add(new StmtAssert(body, new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, tempVar), false));

        return new Example(new StmtBlock(negExBody));
    }

    public static Example extractPositiveExamplePrecisionUnder(Program result) {
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionUnderSketchBuilder.precisionFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        List<Statement> negExBody = new ArrayList<>(stmts.subList(0, numStmts - 9));

        final String tempVarID = "out";
        ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);
        params.add(new ExprVar((FENode) null, tempVarID));

        ExprFunCall newFunCall = new ExprFunCall((FENode) null, Property.newPhiID, params);

        negExBody.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, tempVarID, null));
        negExBody.add(new StmtExpr(newFunCall));
        negExBody.add(new StmtAssert(body, tempVar, false));

        return new Example(new StmtBlock(negExBody));
    }


    public static Example extractNegativeExampleCandidate(Program result, int numHiddenWitness) {
        StmtBlock body = (StmtBlock) findFunction(result, SoundnessUnderSketchBuilder.soundnessUnderFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        // Last numHiddenWitness lines are hidden witnesses (counter-examples),
        // then next 3 lines are synthesized property
        List<Statement> exBody = new ArrayList<>(stmts.subList(0, numStmts - 3 - numHiddenWitness));

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);

        return new Example(exBody, params);
    }

    public static HiddenValue extractHiddenValue(Program result, CommonSketchBuilder builder) {
        StmtBlock body = (StmtBlock) findFunction(result, HiddenWitnessSketchBuilder.hiddenVariableGeneratorID).getBody();
        List<Statement> stmts = new ArrayList<>();
        stmts.add(builder.getVariableDecls(CommonSketchBuilder.ONLY_HIDDEN, CommonSketchBuilder.WO_INIT));
        stmts.addAll(body.getStmts());

        List<Parameter> visibleVar = builder.getVisibleVariableAsParams();
        for (Parameter var : visibleVar)
            if (builder.isOutputVariable(var.getName())) {
                stmts.add(new StmtVarDecl((FENode) null, var.getType(), var.getName() + suffixOfSynthResult,
                        new ExprVar((FENode) null, var.getName())));
            }

        stmts.addAll(builder.getSignatureAsStmts());

        List<String> tempVarIdList = new ArrayList<>();
        for (Parameter var : visibleVar)
            if (builder.isOutputVariable(var.getName())) {
                final String tempVarId = "out_" + var.getName();
                tempVarIdList.add(tempVarId);
                // boolean out_xxx;
                stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVarId, null));
                if (var.getType() instanceof TypePrimitive) {
                    stmts.add(new StmtAssign(new ExprVar((FENode) null, tempVarId),
                            new ExprBinary(ExprBinary.BINOP_EQ, new ExprVar((FENode) null, var.getName()), new ExprVar((FENode) null, var.getName() + suffixOfSynthResult))));
                } else {
                    // todo: support struct
                    throw new SketchConversionException("equality checker needed for user-defined datatype");
                }
            }

//         assert(!(out_x && ...))
        Expression assertCondition = ExprConstInt.one;
        for (String Id : tempVarIdList)
            assertCondition = new ExprBinary(ExprBinary.BINOP_AND, assertCondition, new ExprVar((FENode) null, Id));
        assertCondition = new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, assertCondition);
        stmts.add(new StmtAssert(assertCondition, false));

        return new HiddenValue(new StmtBlock(stmts));
    }


    public static Map<String, Function> extractLambdaFunctions(Program result) {
        return result.getPackages().get(0).getFuncs().stream()
                .filter(f -> f.getName().contains("lam"))
                .collect(Collectors.toMap(Function::getName, f -> f));
    }
}
