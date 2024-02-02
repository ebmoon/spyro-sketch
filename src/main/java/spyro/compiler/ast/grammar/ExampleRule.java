package spyro.compiler.ast.grammar;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.Expression;
import spyro.compiler.ast.type.Type;

import java.util.List;

/**
 * Class for example production rule
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExampleRule extends SpyroNode {

    Nonterminal nonterminal;
    List<RHSTerm> rules;


    public ExampleRule(Nonterminal nonterminal, List<RHSTerm> rules) {
        this.nonterminal = nonterminal;
        this.rules = rules;
    }

    @Override
    public Object accept(SpyroNodeVisitor v) {
        return v.visitExampleRule(this);
    }

    public Nonterminal getNonterminal() {
        return nonterminal;
    }

    public List<RHSTerm> getRules() {
        return rules;
    }

}
