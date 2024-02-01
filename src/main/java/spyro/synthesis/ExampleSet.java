package spyro.synthesis;

import sketch.compiler.ast.core.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for set of examples used in Counterexample-Guided-Inductive-Synthesis (CEGIS)
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ExampleSet {
    List<Example> examples;

    public ExampleSet() {
        this.examples = new ArrayList<>();
    }

    public ExampleSet(List<Example> examples) {
        this.examples = new ArrayList<>(examples);
    }

    public void add(Example ex) {
        examples.add(ex);
    }

    public void clear() {
        examples.clear();
    }

    public ExampleSet copy() { return new ExampleSet(examples); }

    public void merge(ExampleSet E) {
        examples.addAll(E.examples);
    }

    public List<Function> toSketchCode(String prefix) {
        List<Function> exampleConstraints = new ArrayList<>();

        int cnt = 0;
        for (Example ex : examples) {
            exampleConstraints.add(ex.toSketchCode(prefix + "_" + cnt++));
        }

        return exampleConstraints;
    }
}
