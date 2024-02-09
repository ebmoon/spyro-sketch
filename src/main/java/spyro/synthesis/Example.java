package spyro.synthesis;

import org.apache.commons.math3.analysis.function.Exp;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for examples used in Counterexample-Guided-Inductive-Synthesis (CEGIS)
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Example {

    StmtBlock body; // Entire harness body (used for normal primitive)

    List<Statement> initialCode;

    List<Expression> varList;


    public Example(StmtBlock body) {
        this.body = body;
    }

    public Example(List<Statement> initialCode, List<Expression> varList) {
        this.initialCode = initialCode;
        this.varList = varList;
    }

    public Function toSketchCode(String name) {
        Function.FunctionCreator fc = Function.creator((FENode) null, name, Function.FcnType.Harness);
        fc.params(new ArrayList<>());
        fc.body(body);

        return fc.create();
    }
}
