package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for constant Boolean expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSConstBool extends RHSConstant {

    private boolean value;

    public RHSConstBool(boolean value) {
        super();
        this.value = value;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitRHSConstBool(this);
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
