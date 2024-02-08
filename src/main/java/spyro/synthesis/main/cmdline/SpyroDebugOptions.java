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
}
