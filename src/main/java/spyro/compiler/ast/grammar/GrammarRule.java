package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.expr.Variable;

import java.util.List;

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
    public Object accept(SpyroNodeVisitor v) {
        return v.visitGrammarRule(this);
    }

    public Variable getNonterminal() {
        return nonterminal;
    }

    public List<Expression> getRules() {
        return rules;
    }
}
