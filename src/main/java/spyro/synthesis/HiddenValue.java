package spyro.synthesis;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.stmts.StmtBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for hidden witness of examples.
 *
 * @author Xuanyu Peng &lt;xuanyupeng@cs.wisc.edu&gt;
 */

public class HiddenValue {
    StmtBlock body;

    public HiddenValue(StmtBlock body) {
        this.body = body;
    }

    public Function toSketchCode(String name, List<Parameter> params) {
        Function.FunctionCreator fc = Function.creator((FENode) null, name, Function.FcnType.Static);
        fc.params(params);
        fc.body(body);

        return fc.create();
    }
}
