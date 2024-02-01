package spyro.synthesis;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.stmts.StmtBlock;

import java.util.ArrayList;

/**
 * Class for examples used in Counterexample-Guided-Inductive-Synthesis (CEGIS)
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Example {

    StmtBlock body;

    public Example(StmtBlock body) {
        this.body = body;
    }

    public Function toSketchCode(String name) {
        Function.FunctionCreator fc = Function.creator((FENode) null, name, Function.FcnType.Harness);
        fc.params(new ArrayList<>());
        fc.body(body);

        return fc.create();
    }
}
