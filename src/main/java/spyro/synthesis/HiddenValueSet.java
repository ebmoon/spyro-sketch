package spyro.synthesis;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for set of hidden witnesses.
 * It requires an information from query to set its parameter.
 *
 * @author Xuanyu Peng &lt;xuanyupeng@cs.wisc.edu&gt;
 */

public class HiddenValueSet {
    List<HiddenValue> hiddenValues;

    public HiddenValueSet() {
        hiddenValues = new ArrayList<>();
    }

    public List<HiddenValue> getHiddenValues() {
        return hiddenValues;
    }

    public List<Function> toSketchCode(String prefix, List<Parameter> params) {
        List<Function> exampleConstraints = new ArrayList<>();

        int cnt = 0;
        for (HiddenValue ex : hiddenValues) {
            exampleConstraints.add(ex.toSketchCode(prefix + cnt++, params));
        }

        return exampleConstraints;
    }

    public void add(HiddenValue hv) {
        hiddenValues.add(hv);
    }
}
