package spyro.compiler.ast;

public class Variable extends SpyroNode {
	
	private String id;
	private boolean hidden;
	
	public Variable(String id) {
		this.id = String.valueOf(id);
		this.hidden = false;
	}
	
	public Variable(String id, boolean hidden) {
		this.id = String.valueOf(id);
		this.hidden = hidden;
	}
	
	@Override
	public void accept(SpyroNodeVisitor visitor) { visitor.visitVariable(this); }
	
	public boolean isHidden() {
		return this.hidden;
	}
	
	public String getId() {
		return this.id;
	}
}
