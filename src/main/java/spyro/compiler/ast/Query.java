package spyro.compiler.ast;

import spyro.compiler.ast.expr.ExprFuncCall;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;

import java.util.List;

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
    private List<ExampleRule> examples;

    public Query(List<Variable> variables, List<ExprFuncCall> signatures,
                 List<GrammarRule> grammar, List<ExampleRule> examples
    ) {
        super();
        this.variables = variables;
        this.signatures = signatures;
        this.grammar = grammar;
        this.examples = examples;
    }

    @Override
    public Object accept(SpyroNodeVisitor visitor) {
        return visitor.visitQuery(this);
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public List<ExprFuncCall> getSignatures() {
        return this.signatures;
    }

    public List<GrammarRule> getGrammar() {
        return this.grammar;
    }

    public List<ExampleRule> getExamples() {
        return this.examples;
    }
}
