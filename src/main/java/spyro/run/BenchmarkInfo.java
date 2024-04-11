package spyro.run;

import spyro.util.exceptions.RunBenchmarkException;

import java.util.ArrayList;
import java.util.Arrays;

public class BenchmarkInfo {
    String name;
    String spyroFile;
    String sketchFile;
    int propertyType;
    int bndInlineAmnt;
    int bndUnrollAmnt;
    int bndMbits;

//    static int[] seed = new int[]{2333, 6666, 89805};

    public String getName() {
        return name;
    }

    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType, int bndInlineAmnt, int bndUnrollAmnt, int bndMbits) {
        this.name = name;
        this.spyroFile = spyroFile;
        this.sketchFile = sketchFile;
        this.propertyType = propertyType;
        this.bndInlineAmnt = bndInlineAmnt;
        this.bndUnrollAmnt = bndUnrollAmnt;
        this.bndMbits = bndMbits;
    }

//    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType, int bndInlineAmnt, int bndUnrollAmnt, int bndMbits, boolean reuse) {
//        this(name,spyroFile,sketchFile,propertyType,bndInlineAmnt,bndUnrollAmnt,bndMbits);
//        this.reuse = reuse;
//    }

    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType) {
        this(name, spyroFile, sketchFile, propertyType, 5,5, 5);
    }
//    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType, boolean reuse) {
//        this(name, spyroFile, sketchFile, propertyType, 5,5, 5);
//        this.reuse = reuse;
//    }

    public String[] toStringArray(boolean reuse) {
        ArrayList<String> args = new ArrayList<>(Arrays.asList(
                spyroFile,
                sketchFile,
//                "-V",
//                "2",
//                "--synth-no-reuse-hidden"
//                "--debug-no-display-results"
                "--bnd-inline-amnt",
                String.valueOf(bndInlineAmnt),
                "--bnd-unroll-amnt",
                String.valueOf(bndUnrollAmnt),
                "--bnd-mbits",
                String.valueOf(bndMbits)
        ));
        if (propertyType == OVER)
            args.add("--synth-over");
        else if (propertyType == UNDER)
            args.add("--synth-under");
        else if (propertyType == OLD_OVER) {
            // do nothing
        } else throw new RunBenchmarkException("Unknown property type for synthesis");

        if (!reuse)
            args.add("--synth-no-reuse-hidden");

        return args.toArray(new String[0]);
    }

    public static final int OVER = 0;
    public static final int UNDER = 1;
    public static final int OLD_OVER = -1;
}