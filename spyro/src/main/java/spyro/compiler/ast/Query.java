package spyro.compiler.ast;

import java.util.List;

public class Query extends SpyroNode {
	
	private List<Variable> variables;
	
	@Override
	public void accept(SpyroNodeVisitor visitor) { visitor.visitQuery(this); }
	
	public List<Variable> getVariables() {
		return this.variables;
	}
}
