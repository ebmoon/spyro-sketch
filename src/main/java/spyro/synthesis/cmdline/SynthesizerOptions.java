package spyro.synthesis.cmdline;

import sketch.util.cli.CliAnnotatedOptionGroup;
import sketch.util.cli.CliParameter;

/**
 * Options specific to property synthesizer.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SynthesizerOptions extends CliAnnotatedOptionGroup {

    public SynthesizerOptions() {
        super("spyro", "options for property synthesis loop");
    }
}
