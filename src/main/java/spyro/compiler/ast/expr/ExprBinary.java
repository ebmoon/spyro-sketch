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

    public static String binaryOpToString(BinaryOp op) {
        switch (op) {
            case BINOP_ADD:
                return "+";
            case BINOP_SUB:
                return "-";
            case BINOP_MUL:
                return "*";
            case BINOP_DIV:
                return "/";
            case BINOP_MOD:
                return "%";
            case BINOP_AND:
                return "&&";
            case BINOP_OR:
                return "||";
            case BINOP_EQ:
                return "==";
            case BINOP_NEQ:
                return "!=";
            case BINOP_LE:
                return "<=";
            case BINOP_LT:
                return "<";
            case BINOP_GE:
                return ">=";
            case BINOP_GT:
                return ">";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return String.format("(%s %s %s)", left.toString(), binaryOpToString(op), right.toString());
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
