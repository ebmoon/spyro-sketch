package spyro.synthesis.primitives;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;
import spyro.synthesis.PropertySet;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build sketch AST for synthesis query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SynthesisSketchBuilder {

    final CommonSketchBuilder commonBuilder;
    Function synthesisBody = null;

    public SynthesisSketchBuilder(CommonSketchBuilder commonBuilder) {
        this.commonBuilder = commonBuilder;
    }

    Function getSynthesisBody() {
        if (synthesisBody == null) {
            Function.FunctionCreator fc = Function.creator((FEContext) null, Property.newPhiID, Function.FcnType.Harness);

            List<Statement> stmts = new ArrayList<>();

            final String tempVarID = "out";
            List<Parameter> params = commonBuilder.getExtendedParams(tempVarID);
            ExprVar tempVar = new ExprVar((FENode) null, tempVarID);
            String generatorFunctionName = commonBuilder.getPropertyGenerators().get(0).getName();

            ExprFunCall generatorCall = new ExprFunCall((FENode) null, generatorFunctionName, commonBuilder.variableAsExprs);
            stmts.add(new StmtAssign(tempVar, generatorCall));

            Statement body = new StmtBlock((FENode) null, stmts);
            fc.params(params);
            fc.body(body);

            synthesisBody = fc.create();
        }

        return synthesisBody;
    }

    public Program synthesisSketchCode(ExampleSet pos, ExampleSet neg) {
        String pkgName = "Synthesis";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(commonBuilder.getFunctions());
        funcs.addAll(pos.toSketchCode("pos"));
        funcs.addAll(neg.toSketchCode("neg"));
        funcs.addAll(commonBuilder.getPropertyGenerators());
        funcs.add(getSynthesisBody());

        funcs.forEach(func -> func.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }
}
