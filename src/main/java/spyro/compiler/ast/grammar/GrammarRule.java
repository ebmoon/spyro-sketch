package spyro.compiler.ast.grammar;

import java.util.List;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.*;

/**
 * Class for language production rule
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class GrammarRule extends SpyroNode {

	Variable nonterminal;
	List<Expression> rules;

	
	public GrammarRule(Variable nonterminal, List<Expression> rules) {
		this.nonterminal = nonterminal;
		this.rules = rules;
	}
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitGrammarRule(this);
	}
	
}
