package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprUnary;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to build sketch AST for soundness query.
 * It works as decorator of CommonSketchBuilder.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SoundnessOverSketchBuilder {

    final CommonSketchBuilder commonBuilder;
    public final static String soundnessFunctionID = "soundness";
    Function soundnessBody = null;

    public SoundnessOverSketchBuilder(CommonSketchBuilder commonBuilder) {
        this.commonBuilder = commonBuilder;
    }

    private Function getSoundnessBody() {
        if (soundnessBody == null) {
            Function.FunctionCreator fc = Function.creator((FENode) null, soundnessFunctionID, Function.FcnType.Harness);

            List<Statement> stmts = new ArrayList<>();
            stmts.add(commonBuilder.getVariableDecls(CommonSketchBuilder.ONLY_INPUT,CommonSketchBuilder.W_INIT));
            stmts.add(commonBuilder.getVariableDecls(CommonSketchBuilder.ONLY_OUTPUT,CommonSketchBuilder.WO_INIT));
            stmts.addAll(commonBuilder.getSignatureAsStmts());

            final String tempVarID = "out";
            final String phi = Property.phiID;
            ExprVar tempVar = new ExprVar((FENode) null, tempVarID);

            // boolean out;
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVar.getName(), null));
            // synthesized_property(..., out);
            stmts.add(new StmtExpr(new ExprFunCall((FENode) null, phi, commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_VISIBLE, tempVarID))));
            // assert !out;
            stmts.add(new StmtAssert(new ExprUnary((FENode) null, ExprUnary.UNOP_NOT, tempVar), false));

            Statement body = new StmtBlock((FENode) null, stmts);

            fc.params(new ArrayList<>());
            fc.body(body);

            soundnessBody = fc.create();
        }

        return soundnessBody;
    }

    public Program soundnessSketchCode(Property phi, Collection<Function> lambdaFunctions) {
        final String pkgName = CommonSketchBuilder.pkgName;
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(commonBuilder.getFunctions());

        funcs.add(phi.toSketchCode());
        funcs.addAll(commonBuilder.getExampleGenerators());
        funcs.add(this.getSoundnessBody());
        funcs.addAll(lambdaFunctions);

        funcs.forEach(func -> func.setPkg(pkgName));
        structs.forEach(struct -> struct.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }
}
