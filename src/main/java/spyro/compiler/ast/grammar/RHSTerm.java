package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNode;

/**
 * An abstract class for Spyro RHS terms in grammar.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class RHSTerm extends SpyroNode {
    public boolean isConstant() {
        return false;
    }

    abstract public String toString();
}
