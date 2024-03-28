package spyro.run;

import spyro.util.exceptions.RunBenchmarkException;

import java.util.ArrayList;
import java.util.Arrays;

public class BenchmarkInfo {
    String name;
    String spyroFile;
    String sketchFile;
    int propertyType;
    int bndUnrollAmnt;
    int bndMbits;

    static int[] seed = new int[]{2333, 6666, 9807};

    public String getName() {
        return name;
    }

    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType, int bndUnrollAmnt, int bndMbits) {
        this.name = name;
        this.spyroFile = spyroFile;
        this.sketchFile = sketchFile;
        this.propertyType = propertyType;
        this.bndUnrollAmnt = bndUnrollAmnt;
        this.bndMbits = bndMbits;
    }

    public BenchmarkInfo(String name, String spyroFile, String sketchFile, int propertyType) {
        this(name, spyroFile, sketchFile, propertyType, 5, 5);
    }

    public String[] toStringArray() {
        ArrayList<String> args = new ArrayList<>(Arrays.asList(
                spyroFile,
                sketchFile,
                "--bnd-unroll-amnt",
                String.valueOf(bndUnrollAmnt),
                "--bnd-mbits",
                String.valueOf(bndMbits),
                "--slv-seed",
                String.valueOf(seed[0]),
                "-V",
                "2"
//                "--debug-no-display-results"
        ));
        if (propertyType == OVER)
            args.add("--synth-over");
        else if (propertyType == UNDER)
            args.add("--synth-under");
        else if (propertyType == OLD_OVER) {
            // do nothing
        } else throw new RunBenchmarkException("Unknown property type for synthesis");
        return args.toArray(new String[0]);
    }

    public static final int OVER = 0;
    public static final int UNDER = 1;
    public static final int OLD_OVER = -1;
}