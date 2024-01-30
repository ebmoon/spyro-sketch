package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.StmtSpAssert;
import sketch.compiler.ast.core.typs.StructDef;
import spyro.synthesis.ExampleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build sketch AST for synthesis query
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SynthesisSketchBuilder {

    final CommonSketchBuilder commonBuilder;

    public SynthesisSketchBuilder(CommonSketchBuilder commonBuilder) {
        this.commonBuilder = commonBuilder;
    }

    public Program synthesisSketchCode(ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        String pkgName = "Synthesis";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(commonBuilder.getFunctions());
        funcs.addAll(pos.toSketchCode());
        funcs.addAll(negMay.toSketchCode());
        funcs.addAll(negMust.toSketchCode());
        funcs.addAll(commonBuilder.getPropertyGenerators());

        funcs.forEach(func -> func.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }
}
