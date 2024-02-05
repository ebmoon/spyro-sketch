package spyro.compiler.ast.grammar;

import spyro.compiler.ast.expr.Expression;

/**
 * Abstract class for constant
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class RHSConstant extends RHSTerm {
    @Override
    public boolean isConstant() {
        return true;
    }

    public int size() { return 1; }
}
