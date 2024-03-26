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

    @CliParameter(help = "Synthesize over-approximations with additional CEGIS loop")
    public boolean over = false;


    @CliParameter(help = "Do not reuse set of hidden variables for counterexample-guided quantifier elimination")
    public boolean noReuseHidden = false;

    public SynthesizerOptions() {
        super("synth", "options for property synthesis loop");
    }
}
