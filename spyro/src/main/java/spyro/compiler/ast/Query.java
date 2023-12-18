package spyro.compiler.ast;

import java.util.List;

import spyro.compiler.ast.expr.ExprFuncCall;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.grammar.GrammarRule;

/**
 * A property synthesis query containing all the information about
 * variables, signatures, grammar and example domain.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Query extends SpyroNode {
	
	private List<Variable> variables;
	private List<ExprFuncCall> signatures;
	private List<GrammarRule> grammar;
	
	public Query(List<Variable> variables, List<ExprFuncCall> signatures, List<GrammarRule> grammar) {
		super();
		this.variables = variables;
		this.signatures = signatures;
		this.grammar = grammar;
	}
	
	@Override
	public void accept(SpyroNodeVisitor visitor) { visitor.visitQuery(this); }
	
	public List<Variable> getVariables() { return this.variables; }
	public List<ExprFuncCall> getSignatures() { return this.signatures; }
	public List<GrammarRule> getGrammar() { return this.grammar; }
}
