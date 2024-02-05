package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.type.Type;

/**
 * A Spyro variable.
 * Each variable could be either visible or hidden.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSVariable extends RHSTerm {

    private final String id;

    public RHSVariable(String id) {
        super();
        this.id = String.valueOf(id);
    }

    public RHSVariable(Variable v) {
        this.id = v.getID();
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitRHSVariable(this);
    }


    public String getID() {
        return id;
    }

    public String toString() {
        return id;
    }

    public int size() { return 1; }
}
