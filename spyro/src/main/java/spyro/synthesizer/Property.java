package spyro.synthesizer;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import sketch.compiler.ast.core.stmts.StmtBlock;

import java.util.List;

public class Property {

    List<Parameter> params;

    public Property(List<Parameter> params) { this.params = params; }

    Function toSketchCode() {
        Function.FunctionCreator fc = Function.creator((FEContext) null, "obtained_property", Function.FcnType.Static);
        Statement stmt = new StmtAssign((FENode) null,
                new ExprVar((FENode) null, "out"),
                new ExprConstInt(0));
        Statement body = new StmtBlock((FENode) null, stmt);

        fc.params(params);
        fc.body(body);

        return fc.create();
    }
}
