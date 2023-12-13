package spyro.compiler.ast.grammar;

import java.util.List;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.type.*;

public class GrammarRule extends SpyroNode {

	Type type;
	Variable nonterminal;
	List<Expression> rules;
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitGrammarRule(this);
	}
	
}
