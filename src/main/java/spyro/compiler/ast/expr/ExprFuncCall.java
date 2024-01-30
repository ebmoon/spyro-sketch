package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for function call expressions
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExprFuncCall extends Expression {

    private String functionId;
    private List<Expression> args;

    public ExprFuncCall(String functionId, List<Expression> args) {
        super();
        this.functionId = String.valueOf(functionId);
        this.args = new ArrayList<>(args);
    }

    public String getFunctionId() {
        return functionId;
    }

    public List<Expression> getArgs() {
        return args;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitExprFuncCall(this);
    }
}
 