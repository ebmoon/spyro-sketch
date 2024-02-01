package spyro.synthesis.primitives;

import sketch.compiler.ast.core.FENode;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.exprs.ExprFunCall;
import sketch.compiler.ast.core.exprs.ExprVar;
import sketch.compiler.ast.core.exprs.Expression;
import sketch.compiler.ast.core.stmts.*;
import sketch.compiler.ast.core.typs.TypePrimitive;
import spyro.synthesis.Example;
import spyro.synthesis.Property;

import java.util.ArrayList;
import java.util.List;

public class SynthesisResultExtractor extends CommonResultExtractor {

    public SynthesisResultExtractor(Program result) {
        super(result);
    }

    public Property extractProperty() {
        Function prop = findFunction(result, Property.newPhiID);
        return new Property(prop);
    }
}
