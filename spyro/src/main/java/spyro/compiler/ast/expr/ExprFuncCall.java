package spyro.compiler.ast.expr;

import java.util.List;

import spyro.compiler.ast.SpyroNodeVisitor;

import java.util.ArrayList;

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
	
	public String getFunctionId() { return functionId; }
	public List<Expression> getArgs() { return args; }
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitExprFuncCall(this);
	}
}
 