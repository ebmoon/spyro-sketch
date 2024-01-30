package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNode;

/**
 * An abstract class for Spyro expression.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class Expression extends SpyroNode {
    public boolean isConstant() {
        return false;
    }
}
