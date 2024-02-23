package spyro.synthesis.primitives;

import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import spyro.synthesis.HiddenValueSet;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to build sketch AST for soundness query of under-approximation,
 * which is used in the CEGIS loop for generating negative example.
 * It works as decorator of CommonSketchBuilder.
 *
 * @author Xuanyu Peng &lt;xuanyupeng@cs.wisc.edu&gt;
 */

public class SoundnessUnderSketchBuilder {

    final CommonSketchBuilder commonBuilder;
    public final static String soundnessUnderFunctionID = "soundnessUnder";
    public final static String hiddenWitnessPrefix= "counter_example_";

    public SoundnessUnderSketchBuilder(CommonSketchBuilder commonBuilder) {
        this.commonBuilder = commonBuilder;
    }

    private Function getSoundnessUnderBody(HiddenValueSet H) {
            Function.FunctionCreator fc = Function.creator((FEContext) null, soundnessUnderFunctionID, Function.FcnType.Harness);

            List<Statement> stmts = new ArrayList<>();
            stmts.add(commonBuilder.getVariableDecls(CommonSketchBuilder.ONLY_VISIBLE,CommonSketchBuilder.W_INIT));

            // ce_i(e)
            for(int i = 0, sz = H.getHiddenValues().size(); i<sz;i++) {
                String ceID = hiddenWitnessPrefix + i;
                stmts.add(new StmtExpr(new ExprFunCall((FENode) null, ceID, commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_VISIBLE, null))));
            }

            final String tempVarID = "out";
            final String phi = Property.phiID;
            ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

            // boolean out;
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVar.getName(), null));
            // synthesized_property(..., out);
            stmts.add(new StmtExpr(new ExprFunCall((FENode) null, phi, commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_VISIBLE, tempVarID))));
            // assert out;
            stmts.add(new StmtAssert(tempVar, false));

            Statement body = new StmtBlock((FENode) null, stmts);

            fc.params(new ArrayList<>());
            fc.body(body);

            return fc.create();
    }

    public Program soundnessUnderSketchCode(Property phi, HiddenValueSet H, Collection<Function> lambdaFunctions) {
        final String pkgName = CommonSketchBuilder.pkgName;
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(commonBuilder.getFunctions());

        funcs.add(phi.toSketchCode());
        funcs.addAll(commonBuilder.getExampleGenerators());
        funcs.addAll(H.toSketchCode(hiddenWitnessPrefix, commonBuilder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE,null)));
        funcs.add(this.getSoundnessUnderBody(H));
        funcs.addAll(lambdaFunctions);

        funcs.forEach(func -> func.setPkg(pkgName));
        structs.forEach(struct -> struct.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }

}
