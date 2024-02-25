package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.ExprBinary;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;

import java.util.*;

/**
 * Class to build sketch AST for hidden witness query,
 * which is used in the CEGIS loop for generating negative example.
 * It works as decorator of CommonSketchBuilder.
 *
 * @author Xuanyu Peng &lt;xuanyupeng@cs.wisc.edu&gt;
 */
public class HiddenWitnessSketchBuilder {
    public final static String hiddenWitnessFunctionID = "hidden_witness";
    public final static String hiddenVariableGeneratorID = "gen_hidden_variables";
    public final static String suffixOfSynthResult = "_of_the_example";
    final CommonSketchBuilder commonBuilder;
    Function hiddenWitnessBody = null;

    Function hiddenVariableGenerator = null;

    public HiddenWitnessSketchBuilder(CommonSketchBuilder commonBuilder) {
        this.commonBuilder = commonBuilder;
    }

    private Function getHiddenVariableGenerator() {
        if (hiddenVariableGenerator == null) {
            Function.FunctionCreator fc = Function.creator((FEContext) null, hiddenVariableGeneratorID, Function.FcnType.Static);
            List<Statement> stmts = new ArrayList<>(commonBuilder.getHiddenVariablesWithHole());
            stmts.add(new StmtEmpty((FENode) null));
            Statement body = new StmtBlock((FENode) null, stmts);
            fc.params(commonBuilder.getHiddenVarAsParamsRef());
            fc.body(body);
            hiddenVariableGenerator = fc.create();
        }
        return hiddenVariableGenerator;
    }


    private Function getHiddenWitnessBody(Example e) {
        Function.FunctionCreator fc = Function.creator((FEContext) null, hiddenWitnessFunctionID, Function.FcnType.Harness);

        List<Statement> stmts = new ArrayList<>();

        // declare fresh hidden variables
        stmts.add(commonBuilder.getVariableDecls(CommonSketchBuilder.ONLY_HIDDEN, CommonSketchBuilder.WO_INIT));
        // set the hidden variables by generated value
        stmts.add(new StmtExpr(new ExprFunCall((FENode) null, hiddenVariableGeneratorID, commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_HIDDEN, null))));

        // example
        stmts.addAll(e.getInitialCode());
        Map<Parameter, Expression> visibleVarToResultName = new HashMap<>();
        List<Parameter> visibleVar = commonBuilder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE, null);
        for (int i = 0, sz = visibleVar.size(); i < sz; i++) {
            visibleVarToResultName.put(visibleVar.get(i), e.getVarList().get(i));
        }
        // declare new variables to store the synthesis result, e.g.
        // "int x = var_01" if x is an input variable
        // "int x_of_the_example = var_01" if x is an output variable
        for (Map.Entry<Parameter, Expression> entry : visibleVarToResultName.entrySet()) {
            String varId = entry.getKey().getName();
            String declId = varId + (commonBuilder.isOutputVariable(varId) ? suffixOfSynthResult : "");
            stmts.add(new StmtVarDecl((FENode) null, entry.getKey().getType(), declId, entry.getValue()));
        }

        // declare fresh output variables
        stmts.add(commonBuilder.getVariableDecls(CommonSketchBuilder.ONLY_OUTPUT & CommonSketchBuilder.ONLY_VISIBLE, CommonSketchBuilder.WO_INIT));

        // Signature
        stmts.addAll(commonBuilder.getSignatureAsStmts());

        // equality check for output variables
        for (Parameter var : commonBuilder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE & CommonSketchBuilder.ONLY_OUTPUT, null)) {
            final String tempVarId = "out_" + var.getName();
            ExprVar out = new ExprVar((FENode) null, tempVarId);
            ExprVar op1 = new ExprVar((FENode) null, var.getName());
            ExprVar op2 = new ExprVar((FENode) null, var.getName() + suffixOfSynthResult);
            // boolean out_xxx;
            stmts.add(new StmtVarDecl((FENode) null, sketch.compiler.ast.core.typs.TypePrimitive.bittype, tempVarId, null));

            if (var.getType() instanceof TypePrimitive) {
                stmts.add(new StmtAssign(out, new ExprBinary(ExprBinary.BINOP_EQ, op1, op2)));
            } else {
                String funID = var.getType().toString() + CommonSketchBuilder.equalityOperatorSuffix;
                stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                        funID,
                        new ArrayList<>(Arrays.asList(op1, op2, out))
                )));
            }
            stmts.add(new StmtAssert(new ExprVar((FENode) null, tempVarId), false));

        }

        Statement body = new StmtBlock((FENode) null, stmts);

        fc.params(new ArrayList<>());
        fc.body(body);

        hiddenWitnessBody = fc.create();
        return hiddenWitnessBody;
    }

    public Program hiddenWitnessSketchCode(Example e) {
        final String pkgName = CommonSketchBuilder.pkgName;
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<StructDef> structs = commonBuilder.getStructDefinitions();
        List<Function> funcs = new ArrayList<Function>(commonBuilder.getFunctions());

        funcs.addAll(commonBuilder.getExampleGenerators());
        funcs.add(this.getHiddenVariableGenerator());
        funcs.add(this.getHiddenWitnessBody(e));

        funcs.forEach(func -> func.setPkg(pkgName));
        structs.forEach(struct -> struct.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);

        return prog.creator().streams(namespaces).create();
    }


}
