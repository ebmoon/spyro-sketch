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

    private String id;
    private Type type;

    public RHSVariable(Type type, String id) {
        super();
        this.type = type;
        this.id = String.valueOf(id);
    }

    public RHSVariable(Variable v) {
        this.type = v.getType();
        this.id = v.getID();
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitRHSVariable(this);
    }


    public String getID() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return id;
    }

    public int size() { return 1; }

    public String toFullString() {
        return String.format("%s %s", type.toString(), id);
    }
}
