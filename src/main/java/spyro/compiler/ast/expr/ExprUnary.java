package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for unary operator expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprUnary extends Expression {

    private UnaryOp op;
    private Expression expr;
    public ExprUnary(UnaryOp op, Expression expr) {
        super();
        this.op = op;
        this.expr = expr;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitExprUnary(this);
    }

    public UnaryOp getOp() {
        return op;
    }

    public Expression getExpr() {
        return expr;
    }

    public static String unaryOpToString(UnaryOp op) {
        switch (op) {
            case UNOP_NOT:
            case UNOP_BNOT:
                return "!";
            case UNOP_NEG:
                return "~";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return String.format("(%s %s)", unaryOpToString(op), expr.toString());
    }

    public enum UnaryOp {
        UNOP_NOT, UNOP_BNOT, UNOP_NEG,
        // below expressions are not supported now
        UNOP_PREINC, UNOP_POSTINC, UNOP_PREDEC, UNOP_POSTDEC
    }

}
