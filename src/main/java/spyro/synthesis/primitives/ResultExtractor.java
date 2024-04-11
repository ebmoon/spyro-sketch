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
import spyro.util.exceptions.ResultExtractException;

import java.util.*;
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

    public static Integer findStatement(List<Statement> stmts, String funName) {
        for (int i = 0, numStmts = stmts.size(); i < numStmts; i++) {
            Statement stmt = stmts.get(i);
            if (stmt instanceof StmtExpr) {
                Expression expr = ((StmtExpr) stmt).getExpression();
                if (expr instanceof ExprFunCall) {
                    ExprFunCall funCall = (ExprFunCall) expr;
                    if (Objects.equals(funCall.getName(), funName))
                        return i;
                }
            }
        }
        throw new ResultExtractException(String.format("Cannot find function call %s in the code", funName));
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
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionOldOverSketchBuilder.precisionFunctionID).getBody();
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
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionOldOverSketchBuilder.precisionFunctionID).getBody();
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


    public static Example extractNegativeExampleCandidate(Program result, String functionName) {
        StmtBlock body = (StmtBlock) findFunction(result, functionName).getBody();
        List<Statement> stmts = body.getStmts();
        int loc = findStatement(stmts, Property.phiID);
        int numStmts = stmts.size();

        // From the rear of the code to the front,
        // the last `numHiddenWitness` lines are hidden witnesses (counter-examples),
        // then next 3(for soundnessUnder) or 9(for precisionOver) lines are synthesized property
        List<Statement> exBody = new ArrayList<>(stmts.subList(0, loc - 1));

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(loc)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);

        return new Example(exBody, params);
    }

    public static HiddenValue extractHiddenValue(Program result, CommonSketchBuilder builder) {
        Statement body = findFunction(result, HiddenWitnessSketchBuilder.hiddenVariableGeneratorID).getBody();
        List<Statement> stmts = new ArrayList<>();

        // Declare hidden variables and assign them with the value of the witness
        stmts.add(builder.getVariableDecls(CommonSketchBuilder.ONLY_HIDDEN, CommonSketchBuilder.WO_INIT));
        if (body instanceof StmtBlock)
            stmts.addAll(((StmtBlock) body).getStmts());

        // Store the output value of the given example (compared with output value obtained by running the query)
        List<Parameter> visibleOutputVar = builder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE & CommonSketchBuilder.ONLY_OUTPUT, null);
        for (Parameter var : visibleOutputVar)
            stmts.add(new StmtVarDecl((FENode) null, var.getType(), var.getName() + suffixOfSynthResult,
                    new ExprVar((FENode) null, var.getName())));


        // run the query
        stmts.addAll(builder.getSignatureAsStmts());

        // Check whether the output of query and example are equal
        List<String> tempVarIdList = new ArrayList<>();
        for (Parameter var : visibleOutputVar) {
            final String tempVarId = "out_" + var.getName();
            tempVarIdList.add(tempVarId);
            ExprVar out = new ExprVar((FENode) null, tempVarId);
            ExprVar op1 = new ExprVar((FENode) null, var.getName());
            ExprVar op2 = new ExprVar((FENode) null, var.getName() + suffixOfSynthResult);
            // boolean out_xxx;
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVarId, null));

            if (var.getType() instanceof TypePrimitive) {
                stmts.add(new StmtAssign(out, new ExprBinary(ExprBinary.BINOP_EQ, op1, op2)));
            } else {
                String funID = var.getType().toString() + CommonSketchBuilder.equalityOperatorSuffix;
                stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                        funID,
                        new ArrayList<>(Arrays.asList(op1, op2, out))
                )));
            }
        }

//         assert(!(asp && out_1 && out_2 && ...))
        stmts.addAll(builder.getAssumptionAsStmts());
        Expression assertCondition = new ExprVar((FENode) null, CommonSketchBuilder.assumpitonConjunctionId);
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
