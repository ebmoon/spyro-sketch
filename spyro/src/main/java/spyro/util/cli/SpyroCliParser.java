package spyro.util.cli;

import sketch.util.cli.SketchCliParser;

public class SpyroCliParser extends SketchCliParser {
    public SpyroCliParser(String[] args, String usage, boolean errorOnUnknown) {
        super(args, usage, errorOnUnknown);
    }

    public SpyroCliParser(String[] inArgs) {
        super(inArgs);
    }

    public SpyroCliParser(String[] array, boolean errorOnUnknown) {
        super(array, errorOnUnknown);
    }
}
