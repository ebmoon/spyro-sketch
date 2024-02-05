package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.ExprBinary;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.expr.ExprBinary.BinaryOp;

/**
 * Class for binary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RHSBinary extends RHSTerm {

    private BinaryOp op;
    private RHSTerm left, right;
    public RHSBinary(BinaryOp op, RHSTerm left, RHSTerm right) {
        super();
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public BinaryOp getOp() {
        return op;
    }

    public RHSTerm getLeft() {
        return left;
    }

    public RHSTerm getRight() { return right; }

    public int size() { return 1 + left.size() + right.size(); }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitRHSBinary(this);
    }

    public String toString() {
        return String.format("(%s %s %s)", left.toString(), ExprBinary.binaryOpToString(op), right.toString());
    }
}
