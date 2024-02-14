package spyro.synthesis;

import org.apache.commons.math3.analysis.function.Exp;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for examples used in Counterexample-Guided-Inductive-Synthesis (CEGIS)
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Example {

    StmtBlock body; // Entire harness body (used for normal primitive)

    List<Statement> initialCode; // used only for negative example candidates

    List<Expression> varList; // used only for negative example candidates

    public List<Statement> getInitialCode() {
        return initialCode;
    }

    public List<Expression> getVarList() {
        return varList;
    }

    public Example(StmtBlock body) {
        this.body = body;
    }

    public Example(List<Statement> initialCode, List<Expression> varList) {
        this.initialCode = initialCode;
        this.varList = varList;

        // convert to a complete negative example
        List<Statement> negExBody = new ArrayList<>(initialCode);
        final String tempVarID = "out";
        ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

        List<Expression> params = new ArrayList<>(varList);
        params.add(new ExprVar((FENode) null, tempVarID));

        ExprFunCall newFunCall = new ExprFunCall((FENode) null, Property.newPhiID, params);

        negExBody.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, tempVarID, null));
        negExBody.add(new StmtExpr(newFunCall));
        negExBody.add(new StmtAssert(new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, tempVar), false));
        this.body = new StmtBlock(negExBody);
    }

    public Function toSketchCode(String name) {
        Function.FunctionCreator fc = Function.creator((FENode) null, name, Function.FcnType.Harness);
        fc.params(new ArrayList<>());
        fc.body(body);

        return fc.create();
    }
}
