package spyro.compiler.main.cmdline;

import java.io.File;
import java.util.Arrays;

import sketch.compiler.main.cmdline.SketchOptions;
import spyro.compiler.cmdline.PropertyOptions;
import spyro.util.cli.SpyroCliParser;

public class SpyroOptions extends SketchOptions {
	public PropertyOptions propOpts = new PropertyOptions();
	public String spyroName;
	public File spyroFile;
	protected static SpyroOptions _singleton;
	
    public SpyroOptions(String[] inArgs) {
        super(inArgs);
        SpyroCliParser parser = new SpyroCliParser(inArgs);
        parseCommandline(parser);
        _singleton = this;
    }
    
    
    public void parseCommandline(SpyroCliParser parser) {
        this.currentArgs = parser.inArgs;
        this.bndOpts.parse(parser);
        this.debugOpts.parse(parser);
        this.feOpts.parse(parser);
        this.spmdOpts.parse(parser);
        this.semOpts.parse(parser);
        this.propOpts.parse(parser);
        args = solverOpts.parse(parser).get_args();
        this.backendArgs = parser.backendArgs;
        this.nativeArgs = parser.nativeArgs;
        if (args.length < 1 || args[0].equals("")) {
            parser.printHelpAndExit("no files specified");
        }

        // actions
        argsAsList = Arrays.asList(args);
        spyroFile = new File(args[0]);
        spyroName = sketchFile.getName().replaceFirst("\\.+$", "");
        sketchFile = new File(args[1]);
        sketchName = sketchFile.getName().replaceFirst("\\.+$", "");
        feOpts.outputCode |= feOpts.outputTest;
    }
	
    public static SpyroOptions getSingleton() {
        return _singleton;
    }
}
