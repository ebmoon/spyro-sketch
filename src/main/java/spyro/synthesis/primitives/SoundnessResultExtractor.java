package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.List;

public class SoundnessResultExtractor extends CommonResultExtractor {

    public SoundnessResultExtractor(Program result) {
        super(result);
    }

    public Example extractPositiveExample() {
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
}