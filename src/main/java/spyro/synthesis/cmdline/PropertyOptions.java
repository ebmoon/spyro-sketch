package spyro.synthesis.cmdline;

import sketch.util.cli.CliAnnotatedOptionGroup;
import sketch.util.cli.CliParameter;

/**
 * Options specific to property synthesizer.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class PropertyOptions extends CliAnnotatedOptionGroup {
    @CliParameter(help = "The number of disjunct at most")
    public int numAtomMax = 3;
    @CliParameter(help = "Disable minimization of synthesized properties")
    public boolean disableMin = false;

    public PropertyOptions() {
        super("prop", "options for synthesized properties");
    }
}
