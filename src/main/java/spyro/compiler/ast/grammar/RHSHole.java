package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for default hole expression
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSHole extends RHSTerm {

    private int size;

    public RHSHole() {
        super();
        this.size = 0;
    }

    public RHSHole(int size) {
        super();
        this.size = size;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitRHSHole(this);
    }

    public int getSize() {
        return this.size;
    }

    public String toString() {
        if (size == 0) {
            return "??";
        } else {
            return String.format("??(%d)", size);
        }
    }
}
