package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.ExprUnary;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.expr.ExprUnary.UnaryOp;

/**
 * Class for unary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSUnary extends RHSTerm {

    private UnaryOp op;
    private RHSTerm expr;
    public RHSUnary(UnaryOp op, RHSTerm expr) {
        super();
        this.op = op;
        this.expr = expr;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitRHSUnary(this);
    }

    public UnaryOp getOp() {
        return op;
    }

    public RHSTerm getExpr() {
        return expr;
    }

    public int size() { return 1 + expr.size(); }

    public String toString() {
        return String.format("(%s %s)", ExprUnary.unaryOpToString(op), expr.toString());
    }
}
