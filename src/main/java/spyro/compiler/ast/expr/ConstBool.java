package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for constant Boolean expression
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ConstBool extends Constant {

	private boolean value;
	
	public ConstBool(boolean value) {
		super();
		this.value = value;
	}
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitConstBool(this);
	}

	public boolean getValue() { return value; }
}
