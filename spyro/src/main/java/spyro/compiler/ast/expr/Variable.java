package spyro.compiler.ast.expr;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.type.Type;

/**
 * A Spyro variable.
 * Each variable could be either visible or hidden.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Variable extends Expression {
	
	private String id;
	private Type type;
	private boolean hidden;
	
	public Variable(String id) {
		super();
		this.id = String.valueOf(id);
		this.hidden = false;
	}
	
	public Variable(String id, boolean hidden) {
		super();
		this.id = String.valueOf(id);
		this.hidden = hidden;
	}
	
	@Override
	public void accept(SpyroNodeVisitor visitor) { visitor.visitVariable(this); }
	
	public boolean isHidden() { return hidden; }
	public String getId() { return id; }
	public Type getType() { return type; }
}
