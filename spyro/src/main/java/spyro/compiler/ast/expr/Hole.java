package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for default hole expression
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Hole extends Expression {

	private int size;
	
	public Hole() {
		super();
		this.size = 0;
	}
	
	public Hole(int size) {
		super();
		this.size = size;
	}
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitHole(this);
	}

	public int getSize() {
		return this.size;
	}
}
