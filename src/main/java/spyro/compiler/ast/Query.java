package spyro.compiler.ast;

import spyro.compiler.ast.expr.ExprFuncCall;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;

import java.util.ArrayList;
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
    private List<ExprFuncCall> assumptions;

    public Query(List<Variable> variables, List<ExprFuncCall> signatures,
                 List<GrammarRule> grammar, List<ExampleRule> examples
    ) {
        super();
        this.variables = variables;
        this.signatures = signatures;
        this.grammar = grammar;
        this.examples = examples;
        this.assumptions = new ArrayList<>();
    }

    public Query(List<Variable> variables, List<ExprFuncCall> signatures,
                 List<GrammarRule> grammar, List<ExampleRule> examples, List<ExprFuncCall> assupmtions
    ) {
        this(variables, signatures, grammar, examples);
        this.assumptions = assupmtions;
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

    public List<ExprFuncCall> getAssumptions() {
        return assumptions;
    }
}
