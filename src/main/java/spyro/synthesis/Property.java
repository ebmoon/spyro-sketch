package spyro.synthesis;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;

import java.util.List;

/**
 * Class for synthesized property
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Property {

    public final static String phiID = "synthesized_property";
    public final static String newPhiID = "property";

    Function impl;

    public Property(Function impl) { this.impl = impl; }

    public Function toSketchCode() {
        Function.FunctionCreator fc = Function.creator((FENode) null, phiID, Function.FcnType.Static);
        fc.params(impl.getParams());
        fc.body(impl.getBody());

        return fc.create();
    }

    public Function toSketchCode(String name) {
        Function.FunctionCreator fc = Function.creator((FENode) null, name, Function.FcnType.Static);
        fc.params(impl.getParams());
        fc.body(impl.getBody());

        return fc.create();
    }
}
