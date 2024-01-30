package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.type.Type;

import java.util.List;

/**
 * Class for example production rule
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExampleRule extends SpyroNode {

    Type ty;
    List<Expression> rules;


    public ExampleRule(Type ty, List<Expression> rules) {
        this.ty = ty;
        this.rules = rules;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitExampleRule(this);
    }

    public Type getType() {
        return ty;
    }

    public List<Expression> getRules() {
        return rules;
    }

}
