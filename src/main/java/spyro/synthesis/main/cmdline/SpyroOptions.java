package spyro.synthesis.main.cmdline;

import sketch.compiler.main.cmdline.SketchOptions;
import sketch.util.cli.SketchCliParser;
import spyro.synthesis.cmdline.SynthesizerOptions;
import spyro.util.cli.SpyroCliParser;

import java.io.File;
import java.util.Arrays;

/**
 * Class for Spyro options, which extends sketch options to spyro options.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SpyroOptions extends SketchOptions {
    protected static SpyroOptions _singleton;
    // public SynthesizerOptions synthOpts = new SynthesizerOptions();
    public String spyroName;
    public File spyroFile;

    public SpyroDebugOptions debugOpts;

    public SpyroOptions(String[] inArgs) {
        super(inArgs);
        SpyroCliParser parser = new SpyroCliParser(inArgs);
        parseCommandline(parser);
        _singleton = this;
    }

    public static SpyroOptions getSingleton() {
        return _singleton;
    }

    @Override
    public void parseCommandline(SketchCliParser parser) {
        // DO NOTHING
    }

    public void parseCommandline(SpyroCliParser parser) {
        this.currentArgs = parser.inArgs;
        this.bndOpts.parse(parser);
        super.debugOpts = debugOpts = new SpyroDebugOptions();
        this.debugOpts.parse(parser);
        this.feOpts.parse(parser);
        this.spmdOpts.parse(parser);
        this.semOpts.parse(parser);
        // this.synthOpts.parse(parser);
        args = solverOpts.parse(parser).get_args();
        this.backendArgs = parser.backendArgs;
        this.nativeArgs = parser.nativeArgs;
        if (args.length < 2 || args[0].equals("") || args[1].equals("")) {
            parser.printHelpAndExit("no files specified");
        }

        // actions
        spyroFile = new File(args[0]);
        spyroName = spyroFile.getName().replaceFirst("\\.+$", "");
        sketchFile = new File(args[1]);
        sketchName = sketchFile.getName().replaceFirst("\\.+$", "");
        feOpts.outputCode |= feOpts.outputTest;

        args = Arrays.copyOfRange(args, 1, args.length);
        argsAsList = Arrays.asList(args);
    }
}
