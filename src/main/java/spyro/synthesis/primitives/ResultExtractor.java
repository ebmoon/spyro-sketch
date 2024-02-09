package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * Class to parse synthesis result of each call to sketch
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ResultExtractor {

    // For debug
    void enumerateStatements(List<Statement> stmts) {
        int idx = 0;
        for(Statement stmt : stmts) {
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
        StmtBlock body = (StmtBlock) findFunction(result, SoundnessSketchBuilder.soundnessFunctionID).getBody();
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
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionSketchBuilder.precisionFunctionID).getBody();
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
        StmtBlock body = (StmtBlock) findFunction(result, PrecisionSketchBuilder.precisionFunctionID).getBody();
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

    public static Example extractNegativeExampleCandidate(Program result, int numHiddenWitness) {
        StmtBlock body = (StmtBlock) findFunction(result, SoundnessUnderSketchBuilder.soundnessUnderFunctionID).getBody();
        List<Statement> stmts = body.getStmts();
        int numStmts = stmts.size();

        // Last numHiddenWitness lines are hidden witnesses (counter examples),
        // then next 3 lines are synthesized property
        List<Statement> exBody = new ArrayList<>(stmts.subList(0, numStmts - 3 - numHiddenWitness));

        ExprFunCall funCall = (ExprFunCall) ((StmtExpr) stmts.get(numStmts - 2 - numHiddenWitness)).getExpression();
        List<Expression> params = new ArrayList<>(funCall.getParams());
        params.remove(params.size() - 1);

        return new Example(exBody, params);
    }

    public static Map<String, Function> extractLambdaFunctions(Program result) {
        return result.getPackages().get(0).getFuncs().stream()
                .filter(f -> f.getName().contains("lam"))
                .collect(Collectors.toMap(Function::getName, f -> f));
    }
}
