package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.type.Type;

/**
 * A Spyro variable.
 * Each variable could be either visible or hidden.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Nonterminal extends RHSTerm {

    private String id;
    private Type type;

    public Nonterminal(Type type, String id) {
        super();
        this.type = type;
        this.id = String.valueOf(id);
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitNonterminal(this);
    }

    public int size() { return 0; }

    public String getID() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return id;
    }

    public String toFullString() {
        return String.format("%s %s", type.toString(), id);
    }
}
