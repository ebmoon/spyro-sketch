package spyro.synthesis.cmdline;

import sketch.util.cli.CliAnnotatedOptionGroup;
import sketch.util.cli.CliParameter;

/**
 * Options specific to property synthesizer.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SynthesizerOptions extends CliAnnotatedOptionGroup {

    @CliParameter(help = "Synthesize under-approximations instead of over-approximations")
    public boolean under = false;

    public SynthesizerOptions() {
        super("synth", "options for property synthesis loop");
    }
}
