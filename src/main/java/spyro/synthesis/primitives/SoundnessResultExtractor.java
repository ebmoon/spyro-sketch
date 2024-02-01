package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.List;

public class SoundnessResultExtractor {

    StmtBlock body;

    public SoundnessResultExtractor(StmtBlock body) {
        this.body = body;
    }

    private void enumerateStatements(List<Statement> stmts) {
        int idx = 0;
        for(Statement stmt : stmts) {
            System.out.println(idx++);
            System.out.println(stmt.getClass());
            System.out.println(stmt.toString());
        }
    }

    public Example extractPositiveExample() {
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

        enumerateStatements(posExBody);

        return new Example(new StmtBlock(posExBody));
    }
}
