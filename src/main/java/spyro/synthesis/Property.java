package spyro.synthesis;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.ast.core.typs.TypePrimitive;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for synthesized property
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Property {

    public final static String phiID = "synthesized_property";
    public final static String newPhiID = "property";
    public final static String outputVarID = "out";

    Function impl;

    public Property(Function impl) {
        this.impl = impl;
    }

    public static Property truth(List<Parameter> params) {
        Function.FunctionCreator fc = Function.creator((FENode) null, phiID, Function.FcnType.Static);
        ExprVar outputParamVar = new ExprVar((FENode) null, params.get(params.size() - 1).getName());

        fc.params(params);
        fc.body(new StmtBlock(new StmtAssign(outputParamVar, ExprConstInt.one)));

        return new Property(fc.create());
    }

    public static Property falsity(List<Parameter> params) {
        Function.FunctionCreator fc = Function.creator((FENode) null, phiID, Function.FcnType.Static);
        ExprVar outputParamVar = new ExprVar((FENode) null, params.get(params.size() - 1).getName());

        fc.params(params);
        fc.body(new StmtBlock(new StmtAssign(outputParamVar, ExprConstInt.zero)));

        return new Property(fc.create());
    }

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
