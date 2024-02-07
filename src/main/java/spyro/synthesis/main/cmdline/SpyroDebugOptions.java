package spyro.synthesis.main.cmdline;

import sketch.compiler.cmdline.DebugOptions;
import sketch.util.cli.CliParameter;

public class SpyroDebugOptions extends DebugOptions {
    @CliParameter(help = "Dump generated sketch files")
    public boolean dumpSketch = false;
}
