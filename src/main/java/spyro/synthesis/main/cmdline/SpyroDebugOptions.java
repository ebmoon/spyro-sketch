package spyro.synthesis.main.cmdline;

import sketch.compiler.cmdline.DebugOptions;
import sketch.util.cli.CliParameter;

/**
 * Class for additional debug options for spyro.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SpyroDebugOptions extends DebugOptions {
    @CliParameter(help = "Dump generated sketch files")
    public boolean dumpSketch = false;

    @CliParameter(help = "Only compute the size of grammar")
    public boolean printGrammarSize = false;
    @CliParameter(help = "Do not display the synthesis results")
    public boolean noDisplayResults = false;
}
