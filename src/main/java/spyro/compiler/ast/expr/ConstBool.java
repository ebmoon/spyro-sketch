package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for constant Boolean expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ConstBool extends Constant {

    private boolean value;

    public ConstBool(boolean value) {
        super();
        this.value = value;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitConstBool(this);
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
