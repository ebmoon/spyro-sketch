package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.type.Type;

/**
 * A Spyro variable.
 * Each variable could be either visible or hidden.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Variable extends Expression {

    private String id;
    private Type type;
    private boolean hidden;

    private boolean output;

    public Variable(Type type, String id) {
        super();
        this.type = type;
        this.id = String.valueOf(id);
        this.hidden = false;
        this.output = false;
    }

    public Variable(Type type, String id, boolean hidden) {
        super();
        this.type = type;
        this.id = String.valueOf(id);
        this.hidden = hidden;
        this.output = false;
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitVariable(this);
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setOutput() { output = true; }

    public boolean getOutput() { return output; }

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
