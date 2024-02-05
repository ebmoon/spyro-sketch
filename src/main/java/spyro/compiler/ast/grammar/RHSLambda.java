package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * A class for anonymous function
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSLambda extends RHSTerm {
    String param;
    RHSTerm body;

    public RHSLambda(String param, RHSTerm body) {
        this.param = param;
        this.body = body;
    }

    public String getParam() {
        return param;
    }

    public RHSTerm getBody() {
        return body;
    }

    public String toString() {
        return String.format("(%s) -> %s", param, body.toString());
    }

    public int size() {
        return 2 + body.size();
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitRHSAnonFunc(this);
    }
}
