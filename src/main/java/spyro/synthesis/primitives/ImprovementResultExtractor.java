package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
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

public class ImprovementResultExtractor extends CommonResultExtractor {


    public ImprovementResultExtractor(Program result) {
        super(result);
    }

    public Example extractNegativeExample() {
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
}
