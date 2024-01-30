package spyro.synthesis;

import sketch.compiler.ast.core.Function;

import java.util.List;


public class ExampleSet {
    List<Example> exList;

    public void add(Example ex) { exList.add(ex); }

    public void clear() {
        exList.clear();
    }

    public void merge(ExampleSet E) {
        exList.addAll(E.exList);
    }

    public List<Function> toSketchCode() {
        return null;
    }
}
