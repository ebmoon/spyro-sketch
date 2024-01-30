package spyro.compiler.ast.expr;

/**
 * Abstract class for constant
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class Constant extends Expression {
    @Override
    public boolean isConstant() {
        return true;
    }
}
