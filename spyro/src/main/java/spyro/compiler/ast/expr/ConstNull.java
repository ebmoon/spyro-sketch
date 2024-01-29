package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for constant null pointer expression
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ConstNull extends Constant {
	
	public ConstNull() { super(); }
	
	@Override
	public Object accept(SpyroNodeVisitor v) {
		return v.visitConstNull(this);
	}
}
