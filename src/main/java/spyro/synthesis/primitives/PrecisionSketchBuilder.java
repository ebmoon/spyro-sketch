package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;
import spyro.synthesis.PropertySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to build sketch AST for precision query.
 * It works as a decorator of SYnthesisSketchBuilder
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class PrecisionSketchBuilder {

    SynthesisSketchBuilder synth;
    Function precisionBody = null;

    public final static String precisionFunctionID = "precision";

    public PrecisionSketchBuilder(SynthesisSketchBuilder synth) {
        this.synth = synth;
    }

    private Function getPrecisionBody() {
        if (precisionBody == null) {
            Function.FunctionCreator fc = Function.creator((FEContext) null, precisionFunctionID, Function.FcnType.Harness);

            List<Statement> stmts = new ArrayList<>();
            stmts.add(synth.commonBuilder.getVariablesWithHole());

            final String tempVarID = "out";
            ExprVar tempVar1 = new ExprVar((FENode) null, tempVarID + "1");
            ExprVar tempVar2 = new ExprVar((FENode) null, tempVarID + "2");
            ExprVar tempVar3 = new ExprVar((FENode) null, tempVarID + "3");

            // boolean out1;
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVar1.getName(), null));
            // synthesized_property(..., out);
            stmts.add(new StmtExpr(new ExprFunCall((FENode) null, Property.phiID,
                    synth.commonBuilder.appendToVariableAsExprs(tempVar1)))); // obtained_property(... , out);
            // assert out;
            stmts.add(new StmtAssert(tempVar1, false));

            // boolean out2
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVar2.getName(), null));
            // property_conj(..., out);
            stmts.add(new StmtExpr(new ExprFunCall((FENode) null, PropertySet.conjunctionID,
                    synth.commonBuilder.appendToVariableAsExprs(tempVar2))));
            // assert out;
            stmts.add(new StmtAssert(tempVar2, false));


            // boolean out3
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVar3.getName(), null));
            // property(..., out);
            stmts.add(new StmtExpr(new ExprFunCall((FENode) null, Property.newPhiID,
                    synth.commonBuilder.appendToVariableAsExprs(tempVar3))));
            // assert !out;
            stmts.add(new StmtAssert(new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, tempVar3), false));

            Statement body = new StmtBlock((FENode) null, stmts);
            fc.params(new ArrayList<>());
            fc.body(body);

            precisionBody = fc.create();
        }

        return precisionBody;
    }

    public Program precisionSketchCode(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg, Collection<Function> lambdaFunctions) {
        final String pkgName = CommonSketchBuilder.pkgName;
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = synth.commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(synth.commonBuilder.getFunctions());

        funcs.addAll(pos.toSketchCode("pos"));
        funcs.addAll(neg.toSketchCode("neg"));
        funcs.add(phi.toSketchCode());
        funcs.addAll(psi.toSketchCode());
        funcs.addAll(synth.commonBuilder.getExampleGenerators());
        funcs.addAll(synth.commonBuilder.getPropertyGenerators());
        funcs.add(synth.getSynthesisBody());
        funcs.add(getPrecisionBody());
        funcs.addAll(lambdaFunctions);

        funcs.forEach(func -> func.setPkg(pkgName));
        structs.forEach(struct -> struct.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }
}
