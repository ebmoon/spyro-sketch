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

    Nonterminal nonterminal;
    List<RHSTerm> rules;


    public GrammarRule(Nonterminal nonterminal, List<RHSTerm> rules) {
        this.nonterminal = nonterminal;
        this.rules = rules;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitGrammarRule(this);
    }

    public Nonterminal getNonterminal() {
        return nonterminal;
    }

    public List<RHSTerm> getRules() {
        return rules;
    }
}
