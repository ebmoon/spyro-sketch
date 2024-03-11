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
    private String generatorID;

    private boolean output;

    public Variable(Type type, String id) {
        super();
        this.type = type;
        this.id = id;
        this.generatorID = null;
        this.hidden = false;
        this.output = false;
    }

    public Variable(Type type, String id, String generatorID) {
        this(type, id);
        this.generatorID = generatorID;
    }

    public Variable(Type type, String id, boolean hidden) {
        this(type, id);
        this.hidden = hidden;
    }

    public Variable(Type type, String id, String generatorID, boolean hidden) {
        this(type, id, hidden);
        this.generatorID = generatorID;
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitVariable(this);
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput() { output = true; }


    public String getID() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getGeneratorID() { return generatorID; }

    public String toString() {
        return id;
    }

    public String toFullString() {
        return String.format("%s %s", type.toString(), id);
    }

    public int varKind() {
        int h = this.isHidden() ? 1 : 0, o = this.isOutput() ? 1 : 0;
        return (1 - h) | (h << 1) | ((1 - o) << 2) | (o << 3);
    }
}
