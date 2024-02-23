package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprConstInt;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build sketch AST for formula-minimizing-synthesis query.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class MinimizationSynthesisSketchBuilder extends SynthesisSketchBuilder {

    public MinimizationSynthesisSketchBuilder(MinimizationSketchBuilder minBuilder) {
        super(minBuilder);
    }

    @Override
    Function getSynthesisBody() {
        if (synthesisBody == null) {
            Function.FunctionCreator fc = Function.creator((FEContext) null, Property.newPhiID, Function.FcnType.Static);

            List<Statement> stmts = new ArrayList<>();

            final String tempVarID = "out";
            List<Parameter> params = commonBuilder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE,  tempVarID);
            Expression sizeVar = new ExprVar((FENode) null, MinimizationSketchBuilder.sizeVarID);
            Expression tempVar = new ExprVar((FENode) null, tempVarID);
            String generatorFunctionName = commonBuilder.getPropertyGenerators().get(0).getName();

            List<Expression> args = new ArrayList<>(commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_VISIBLE, null));
            args.add(sizeVar);
            args.add(tempVar);

            ExprFunCall generatorCall = new ExprFunCall((FENode) null, generatorFunctionName, args);

            stmts.add(new StmtVarDecl((FENode) null, TypePrimitive.inttype, MinimizationSketchBuilder.sizeVarID, ExprConstInt.zero));
            stmts.add(new StmtExpr(generatorCall));
            stmts.add(new StmtMinimize(sizeVar, false));

            Statement body = new StmtBlock((FENode) null, stmts);
            fc.params(params);
            fc.body(body);

            synthesisBody = fc.create();
        }

        return synthesisBody;
    }
}
