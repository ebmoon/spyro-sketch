package spyro.compiler.ast;

import java.util.List;

import spyro.compiler.ast.expr.Variable;

/**
 * A property synthesis query containing all the information about
 * variables, signatures, grammar and example domain.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Query extends SpyroNode {
	
	private List<Variable> variables;
	
	@Override
	public void accept(SpyroNodeVisitor visitor) { visitor.visitQuery(this); }
	
	public List<Variable> getVariables() {
		return this.variables;
	}
}
