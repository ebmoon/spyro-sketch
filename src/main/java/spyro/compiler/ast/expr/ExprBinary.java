package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for binary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprBinary extends Expression {

    private BinaryOp op;
    private Expression left, right;
    public ExprBinary(BinaryOp op, Expression left, Expression right) {
        super();
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public BinaryOp getOp() {
        return op;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitExprBinary(this);
    }

    public enum BinaryOp {
        BINOP_ADD, BINOP_SUB, BINOP_MUL, BINOP_DIV, BINOP_MOD,
        BINOP_AND, BINOP_OR,
        BINOP_EQ, BINOP_NEQ, BINOP_LT, BINOP_LE, BINOP_GT, BINOP_GE,
        // below expressions are not supported now
        BINOP_BAND, BINOP_BOR, BINOP_BXOR,
        BINOP_LSHIFT, BINOP_RSHIFT
    }

}
