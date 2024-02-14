package spyro.synthesis;

import sketch.compiler.ast.core.FEContext;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.primitives.CommonSketchBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for synthesized properties.
 * It requires a information from query to set its parameter.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class PropertySet {
    public final static String conjunctionID = "property_conj";

    public final static String disjunctionID = "property_disj";

    final CommonSketchBuilder commonBuilder;

    List<Function> conjunctionSketchCode = null;
    List<Function> disjunctionSketchCode = null;
    List<Property> properties;

    public PropertySet(CommonSketchBuilder commonBuilder) {
        this.properties = new ArrayList<>();
        this.commonBuilder = commonBuilder;
    }

    public PropertySet(CommonSketchBuilder commonBuilder, List<Property> properties) {
        this.properties = new ArrayList<>(properties);
        this.commonBuilder = commonBuilder;
    }

    public PropertySet copy() {
        return new PropertySet(commonBuilder, properties);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void add(Property phi) {
        properties.add(phi);
        conjunctionSketchCode = null;
        disjunctionSketchCode = null;
    }

    public void addAll(Collection<Property> phis) {
        properties.addAll(phis);
        conjunctionSketchCode = null;
        disjunctionSketchCode = null;
    }

    private Expression conjunction(List<Expression> exprs) {
        if (exprs.isEmpty()) {
            return ExprConstInt.one;
        }

        Expression e = exprs.get(0);
        for (int i = 1; i < exprs.size(); i++) {
            e = new ExprBinary(ExprBinary.BINOP_AND, e, exprs.get(i));
        }

        return e;
    }

    private Expression disjunction(List<Expression> exprs) {
        if (exprs.isEmpty()) {
            return ExprConstInt.zero;
        }

        Expression e = exprs.get(0);
        for (int i = 1; i < exprs.size(); i++) {
            e = new ExprBinary(ExprBinary.BINOP_OR, e, exprs.get(i));
        }

        return e;
    }

    private Expression toFunCall(Function fn, List<Expression> params, Expression outVar) {
        List<Expression> paramsWithOut = new ArrayList<>(params);
        paramsWithOut.add(outVar);
        return new ExprFunCall((FENode) null, fn.getName(), paramsWithOut);
    }

    public List<Function> toSketchCode() {
        boolean codeKind = commonBuilder.apporx;
        List<Function> sketchCode = codeKind ? disjunctionSketchCode : conjunctionSketchCode;
        if (sketchCode == null) {
            sketchCode = new ArrayList<>();

            Function.FunctionCreator fc = Function.creator((FEContext) null, codeKind ? disjunctionID : conjunctionID, Function.FcnType.Static);
            final String tempVarID = "out";
            List<Parameter> params = commonBuilder.getExtendedParams(tempVarID, false);
            List<Expression> vars = commonBuilder.getVariableAsExprs(CommonSketchBuilder.ONLY_VISIBLE);

            List<Statement> body = new ArrayList<>();
            List<Expression> outVars = new ArrayList<>();

            int idx = 0;
            for (Property prop : properties) {
                Function f = prop.toSketchCode(Property.phiID + "_" + idx);
                ExprVar outVar = new ExprVar((FENode) null, "out_" + idx++);

                sketchCode.add(f);
                body.add(new StmtVarDecl((FENode) null, TypePrimitive.bittype, outVar.getName(), null));
                body.add(new StmtExpr(toFunCall(f, vars, outVar)));
                outVars.add(outVar);
            }

            ExprVar lastParamVar = new ExprVar((FENode) null, tempVarID);
            body.add(new StmtAssign(lastParamVar, codeKind ? disjunction(outVars) : conjunction(outVars)));

            fc.params(params);
            fc.body(new StmtBlock(body));

            sketchCode.add(fc.create());
        }

        return sketchCode;
    }

    public static final boolean CONJUNCTION_CODE = false;
    public static final boolean DISJUNCTION_CODE = true;

}
