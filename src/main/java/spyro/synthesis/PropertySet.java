package spyro.synthesis;

import sketch.compiler.ast.core.FEContext;
import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.exprs.*;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.Statement;
import sketch.compiler.ast.core.stmts.StmtAssign;
import spyro.synthesis.primitives.CommonSketchBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for synthesized properties
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class PropertySet {
    public final static String conjunctionID = "property_conj";

    final CommonSketchBuilder commonBuilder;
    List<Function> sketchCode = null;
    List<Property> properties;

    public PropertySet(CommonSketchBuilder commonBuilder) {
        this.properties = new ArrayList<>();
        this.commonBuilder = commonBuilder;
    }

    public PropertySet(CommonSketchBuilder commonBuilder, List<Property> properties) {
        this.properties = new ArrayList<>(properties);
        this.commonBuilder = commonBuilder;
    }

    public PropertySet copy() { return new PropertySet(commonBuilder, properties); }

    public List<Property> getProperties() {
        return properties;
    }

    public void add(Property phi) {
        properties.add(phi);
        sketchCode = null;
    }

    public void addAll(Collection<Property> phis) {
        properties.addAll(phis);
        sketchCode = null;
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

    private Expression toFunCall(Function fn, List<Expression> params) {
        return new ExprFunCall((FENode) null, fn.getName(), params);
    }

    public List<Function> toSketchCode() {
        if (sketchCode == null) {
            sketchCode = new ArrayList<>();

            int idx = 0;
            for (Property prop : properties) {
                String id = String.format("%s_%d", Property.phiID, idx++);
                sketchCode.add(prop.toSketchCode(id));
            }

            Function.FunctionCreator fc = Function.creator((FEContext) null, conjunctionID, Function.FcnType.Static);
            List<Parameter> params = commonBuilder.getPropertyGenerators().get(0).getParams();
            List<Expression> paramsAsVar = params.stream()
                    .map(param -> new ExprVar((FENode) null, param.getName()))
                    .collect(Collectors.toList());
            List<Expression> functionCalls = sketchCode.stream()
                    .map(f -> toFunCall(f, paramsAsVar))
                    .collect(Collectors.toList());;
            ExprVar lastParamVar = (ExprVar) paramsAsVar.get(params.size() - 1);
            Statement body = new StmtAssign(lastParamVar, conjunction(functionCalls));

            fc.params(params);
            fc.body(body);

            sketchCode.add(fc.create());
        }
        return sketchCode;
    }
}
