package spyro.synthesizer;

import sketch.compiler.ast.core.Package;
import sketch.compiler.ast.core.*;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.StructDef;
import sketch.compiler.ast.core.typs.Type;
import spyro.compiler.ast.expr.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimitiveConstructor {

    GeneratorBuilder generatorBuilder;

    List<Function> implementations;

    Function soundnessBody;
    Function precisionBody;

    public PrimitiveConstructor(GeneratorBuilder generatorBuilder, Program sketchPart) {
        this.generatorBuilder = generatorBuilder;
        this.implementations = sketchPart.getPackages().get(0).getFuncs();
        this.soundnessBody = createSoundnessBody();
        this.precisionBody = createPrecisionBody();
    }

    Statement getVariablesWithHole() {
        List<Type> types = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<sketch.compiler.ast.core.exprs.Expression> inits = new ArrayList<>();

        for (Map.Entry<Variable, Type> entry : generatorBuilder.getVariableSketchType().entrySet()) {
            names.add(entry.getKey().getId());
            types.add(entry.getValue());
            String funId = String.format(
                    "%s_gen",
                    entry.getKey().getType().toString()
            );
            inits.add(new ExprFunCall((FENode) null, funId, new ArrayList<>()));
        }

        return new StmtVarDecl((FENode) null, types, names, inits);
    }



    Function createSoundnessBody() {
        Function.FunctionCreator fc = Function.creator((FEContext) null, "soundness", Function.FcnType.Harness);

        List<Statement> stmts = new ArrayList<>();

        stmts.add(getVariablesWithHole());
        for (ExprFunCall sig : generatorBuilder.signatures)
            stmts.add(new StmtExpr(sig));


        stmts.add(new StmtVarDecl((FENode) null,
                sketch.compiler.ast.core.typs.TypePrimitive.bittype,
                "out",
                new ExprConstInt(0))); // boolean out = 0;

        stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                "obtained_property",
                generatorBuilder.appendToVariableAsExprs("out")))); // obtained_property(... , out);

        stmts.add(new StmtAssert(new ExprUnary("!", new ExprVar((FENode) null, "out")), false)); // assert !out;

        Statement body = new StmtBlock((FENode) null, stmts);

        fc.params(new ArrayList<>());
        fc.body(body);

        return fc.create();
    }

    Function createPrecisionBody() {
        Function.FunctionCreator fc = Function.creator((FEContext) null, "precision", Function.FcnType.Harness);

        List<Statement> stmts = new ArrayList<>();

        stmts.add(getVariablesWithHole());
        for (ExprFunCall sig : generatorBuilder.signatures)
            stmts.add(new StmtExpr(sig));

        stmts.add(new StmtVarDecl((FENode) null,
                sketch.compiler.ast.core.typs.TypePrimitive.bittype,
                "out_1",
                new ExprConstInt(0))); // boolean out_1 = 0;

        stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                "obtained_property",
                generatorBuilder.appendToVariableAsExprs("out_1")))); // obtained_property(... , out);

        stmts.add(new StmtAssert(new ExprVar((FENode) null, "out_1"), false)); // assert !out;


        stmts.add(new StmtVarDecl((FENode) null,
                sketch.compiler.ast.core.typs.TypePrimitive.bittype,
                "out_2",
                new ExprConstInt(0))); // boolean out = 0;

        stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                "property_conj",
                generatorBuilder.appendToVariableAsExprs("out_2")))); // obtained_property(... , out);

        stmts.add(new StmtAssert( new ExprVar((FENode) null, "out_2"), false)); // assert !out;


        stmts.add(new StmtVarDecl((FENode) null,
                sketch.compiler.ast.core.typs.TypePrimitive.bittype,
                "out_3",
                new ExprConstInt(0))); // boolean out = 0;

        stmts.add(new StmtExpr(new ExprFunCall((FENode) null,
                "property",
                generatorBuilder.appendToVariableAsExprs("out_3")))); // obtained_property(... , out);

        stmts.add(new StmtAssert(new ExprUnary("!", new ExprVar((FENode) null, "out_3")), false)); // assert !out;

        Statement body = new StmtBlock((FENode) null, stmts);

        fc.params(new ArrayList<>());
        fc.body(body);

        return fc.create();
    }

    public Program constructSoundnessSketchCode(Property phi) {
        String pkgName = "CheckSoundness";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StructDef> structs = new ArrayList<StructDef>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<Function> funcs = new ArrayList<Function>(implementations);
        funcs.add(phi.toSketchCode());
        funcs.addAll(generatorBuilder.getTypeGenerators());
        funcs.add(soundnessBody);

        funcs.forEach(func -> func.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);
        prog = prog.creator().streams(namespaces).create();
        return prog;

    }

    public Program constructPrecisionSketchCode(Property phi, PropertySet phi_list, ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        String pkgName = "CheckPrecision";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StructDef> structs = new ArrayList<StructDef>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<Function> funcs = new ArrayList<Function>(implementations);
        funcs.addAll(pos.toSketchCode());
        funcs.addAll(negMay.toSketchCode());
        funcs.addAll(negMust.toSketchCode());
        funcs.add(phi.toSketchCode());
        funcs.addAll(phi_list.toSketchCode());
        funcs.addAll(generatorBuilder.getTypeGenerators());
        funcs.addAll(generatorBuilder.getNonTerminalGenerators());
        funcs.add(precisionBody);

        funcs.forEach(func -> func.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);
        prog = prog.creator().streams(namespaces).create();
        return prog;
    }

    public Program constructSynthesisSketchCode(ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        String pkgName = "Synthesis";
        List<ExprVar> vars = new ArrayList<ExprVar>();
        List<StructDef> structs = new ArrayList<StructDef>();
        List<StmtSpAssert> specialAsserts = new ArrayList<StmtSpAssert>();
        List<Package> namespaces = new ArrayList<Package>();

        Program prog = Program.emptyProgram();

        List<Function> funcs = new ArrayList<Function>(implementations);
        funcs.addAll(pos.toSketchCode());
        funcs.addAll(negMay.toSketchCode());
        funcs.addAll(negMust.toSketchCode());
        funcs.addAll(generatorBuilder.getNonTerminalGenerators());

        funcs.forEach(func -> func.setPkg(pkgName));

        Package pkg = new Package((FENode) null, pkgName, structs, vars, funcs, specialAsserts);
        namespaces.add(pkg);
        prog = prog.creator().streams(namespaces).create();
        return prog;
    }


}
