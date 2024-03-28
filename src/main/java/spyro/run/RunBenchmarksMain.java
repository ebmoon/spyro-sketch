package spyro.run;

import sketch.compiler.main.other.ErrorHandling;
import sketch.util.exceptions.SketchException;
import spyro.synthesis.RunningResults;
import spyro.synthesis.main.Spyro;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunBenchmarksMain {

    static List<BenchmarkInfo> specOver = new ArrayList<>(
            Arrays.asList(
//                    new BenchmarkInfo("max2", "examples/spec/sygus/max2.sp", "examples/spec/sygus/max2.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("max3", "examples/spec/sygus/max3.sp", "examples/spec/sygus/max3.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("max4", "examples/spec/sygus/max4.sp", "examples/spec/sygus/max4.sk", BenchmarkInfo.OVER),
// //                        new BenchmarkInfo("max5", "examples/spec/sygus/max5.sp", "examples/spec/sygus/max5.sk", BenchmarkInfo.OVER), // timeout
//                    new BenchmarkInfo("array_search_2", "examples/spec/sygus/array_search_2.sp", "examples/spec/sygus/array_search_2.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("diff", "examples/spec/sygus/diff.sp", "examples/spec/sygus/diff.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("abs1", "examples/spec/LIA/abs1.sp", "examples/spec/LIA/abs1.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("abs2", "examples/spec/LIA/abs2.sp", "examples/spec/LIA/abs2.sk", BenchmarkInfo.OVER, 5, 10),
//                    new BenchmarkInfo("linearSum1", "examples/spec/arithmetic/linearSum1.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("linearSum2", "examples/spec/arithmetic/linearSum2.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.OVER,5,7),
                    new BenchmarkInfo("nonLinearSum1", "examples/spec/arithmetic/nonLinearSum1.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("nonLinearSum2", "examples/spec/arithmetic/nonLinearSum2.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.OVER, 5, 10)
            )
    );
    ;
    static List<BenchmarkInfo> specUnder = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("max2", "examples/spec/sygus/max2-under.sp", "examples/spec/sygus/max2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("max3", "examples/spec/sygus/max3-under.sp", "examples/spec/sygus/max3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("max4", "examples/spec/sygus/max4-under.sp", "examples/spec/sygus/max4.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("max5", "examples/spec/sygus/max5-under.sp", "examples/spec/sygus/max5.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("array_search_2", "examples/spec/sygus/array_search_2-under.sp", "examples/spec/sygus/array_search_2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("diff", "examples/spec/sygus/diff-under.sp", "examples/spec/sygus/diff.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("abs1", "examples/spec/LIA/abs1-under.sp", "examples/spec/LIA/abs1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("abs2", "examples/spec/LIA/abs2-under.sp", "examples/spec/LIA/abs2.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("linearSum1", "examples/spec/arithmetic/linearSum1-under.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("linearSum2", "examples/spec/arithmetic/linearSum2-under.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("nonLinearSum1", "examples/spec/arithmetic/nonLinearSum1-under.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("nonLinearSum2", "examples/spec/arithmetic/nonLinearSum2-under.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.UNDER, 5, 8)
            )
    );
    ;
    static List<BenchmarkInfo> specOldOver = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("max2", "examples/spec/sygus/max2.sp", "examples/spec/sygus/max2.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("max3", "examples/spec/sygus/max3.sp", "examples/spec/sygus/max3.sk", BenchmarkInfo.OLD_OVER),
//                    new BenchmarkInfo("max4", "examples/spec/sygus/max4.sp", "examples/spec/sygus/max4.sk", BenchmarkInfo.OLD_OVER),
//                        new BenchmarkInfo("max5", "examples/spec/sygus/max5.sp", "examples/spec/sygus/max5.sk", BenchmarkInfo.OLD_OVER), // timeout
                    new BenchmarkInfo("array_search_2", "examples/spec/sygus/array_search_2.sp", "examples/spec/sygus/array_search_2.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("diff", "examples/spec/sygus/diff.sp", "examples/spec/sygus/diff.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("abs1", "examples/spec/LIA/abs1.sp", "examples/spec/LIA/abs1.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("abs2", "examples/spec/LIA/abs2.sp", "examples/spec/LIA/abs2.sk", BenchmarkInfo.OLD_OVER, 5, 7),
                    new BenchmarkInfo("linearSum1", "examples/spec/arithmetic/linearSum1.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("linearSum2", "examples/spec/arithmetic/linearSum2.sp", "examples/spec/arithmetic/linearSum.sk", BenchmarkInfo.OLD_OVER, 5, 7),
                    new BenchmarkInfo("nonLinearSum1", "examples/spec/arithmetic/nonLinearSum1.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.OLD_OVER),
                    new BenchmarkInfo("nonLinearSum2", "examples/spec/arithmetic/nonLinearSum2.sp", "examples/spec/arithmetic/nonLinearSum.sk", BenchmarkInfo.OLD_OVER, 5, 8)
            )
    );

    static List<BenchmarkInfo> specNondeterOver = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("math1", "examples/new/nondeter/math1-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER,5,7),
                    new BenchmarkInfo("math2", "examples/new/nondeter/math2-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER,5,7),
                    new BenchmarkInfo("math3", "examples/new/nondeter/math3-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER,5,7),
                    new BenchmarkInfo("math4", "examples/new/nondeter/math4-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER,5,7),
                    new BenchmarkInfo("modhash", "examples/new/nondeter/modhash-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER),
//                    new BenchmarkInfo("coin", "examples/new/nondeter/coin-over.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.OVER, 5, 7),
                    new BenchmarkInfo("bv1", "examples/new/nondeter/bv1-over.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("bv2", "examples/new/nondeter/bv2-over.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("bv3-1", "examples/new/nondeter/bv3-1-over.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("bv3-2", "examples/new/nondeter/bv3-2-over.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("bv4", "examples/new/nondeter/bv4-over.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.OVER)
            )

    );
    static List<BenchmarkInfo> specNondeterUnder = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("math1", "examples/new/nondeter/math1-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("math2", "examples/new/nondeter/math2-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("math3", "examples/new/nondeter/math3-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("math4", "examples/new/nondeter/math4-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("modhash", "examples/new/nondeter/modhash-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("coin", "examples/new/nondeter/coin-under.sp", "examples/new/nondeter/math.sk", BenchmarkInfo.UNDER, 5, 7),
                    new BenchmarkInfo("bv1", "examples/new/nondeter/bv1-under.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bv2", "examples/new/nondeter/bv2-under.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bv3-1", "examples/new/nondeter/bv3-1-under.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bv3-2", "examples/new/nondeter/bv3-2-under.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bv4", "examples/new/nondeter/bv4-under.sp", "examples/new/nondeter/bv.sk", BenchmarkInfo.UNDER)
            )
    );
    static List<BenchmarkInfo> specConcurrencyOver = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("philosopher", "examples/new/concurrency/philosopher-over.sp", "examples/new/concurrency/philosophersk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("race1", "examples/new/concurrency/race1-over.sp", "examples/new/concurrency/race1sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("race2", "examples/new/concurrency/race2-over.sp", "examples/new/concurrency/race2sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("race3", "examples/new/concurrency/race3-over.sp", "examples/new/concurrency/race3", BenchmarkInfo.OVER),
                    new BenchmarkInfo("resource1", "examples/new/concurrency/resource1-over.sp", "examples/new/concurrency/resource1.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("resource2", "examples/new/concurrency/resource2-over.sp", "examples/new/concurrency/resource2.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("resource3", "examples/new/concurrency/resource3-over.sp", "examples/new/concurrency/resource3.sk", BenchmarkInfo.OVER),
                    new BenchmarkInfo("resource4", "examples/new/concurrency/resource4-over.sp", "examples/new/concurrency/resource4.sk", BenchmarkInfo.OVER)
            )
    );
    static List<BenchmarkInfo> specConcurrencyUnder = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("philosopher", "examples/new/concurrency/philosopher-under.sp", "examples/new/concurrency/philosopher.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("race1", "examples/new/concurrency/race1-under.sp", "examples/new/concurrency/race1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("race2", "examples/new/concurrency/race2-under.sp", "examples/new/concurrency/race2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("race3", "examples/new/concurrency/race3-under.sp", "examples/new/concurrency/race3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("resource1", "examples/new/concurrency/resource1-under.sp", "examples/new/concurrency/resource1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("resource2", "examples/new/concurrency/resource2-under.sp", "examples/new/concurrency/resource2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("resource3", "examples/new/concurrency/resource3-under.sp", "examples/new/concurrency/resource3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("resource4", "examples/new/concurrency/resource4-under.sp", "examples/new/concurrency/resource4.sk", BenchmarkInfo.UNDER)
            )
    );
    static List<BenchmarkInfo> specWR = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("inc-logic1", "examples/new/wr/inc-logic1.sp", "examples/new/wr/inc-logic1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic2-0", "examples/new/wr/inc-logic2-0.sp", "examples/new/wr/inc-logic2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic2-1", "examples/new/wr/inc-logic2-1.sp", "examples/new/wr/inc-logic2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic3-0", "examples/new/wr/inc-logic3-0.sp", "examples/new/wr/inc-logic3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic3-1", "examples/new/wr/inc-logic3-1.sp", "examples/new/wr/inc-logic3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic4-0", "examples/new/wr/inc-logic4-0.sp", "examples/new/wr/inc-logic4.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic4-1", "examples/new/wr/inc-logic4-1.sp", "examples/new/wr/inc-logic4.sk", BenchmarkInfo.UNDER)
            )
    );
    static List<BenchmarkInfo> specWPP = new ArrayList<>(
            Arrays.asList(
                    new BenchmarkInfo("inc-logic-wpp1", "examples/new/wpp/inc-logic-wpp1.sp", "examples/new/wpp/inc-logic-wpp1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic-wpp2", "examples/new/wpp/inc-logic-wpp2.sp", "examples/new/wpp/inc-logic-wpp2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic-wpp3", "examples/new/wpp/inc-logic-wpp3.sp", "examples/new/wpp/inc-logic-wpp3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("inc-logic-wpp4", "examples/new/wpp/inc-logic-wpp4.sp", "examples/new/wpp/inc-logic-wpp4.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("hashfunc", "examples/new/wpp/hashfunc.sp", "examples/new/wpp/hashfunc.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("arith1", "examples/new/wpp/arith1.sp", "examples/new/wpp/arith1.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("arith2", "examples/new/wpp/arith2.sp", "examples/new/wpp/arith2.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("swap3", "examples/new/wpp/swap3.sp", "examples/new/wpp/swap3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("swap4", "examples/new/wpp/swap4.sp", "examples/new/wpp/swap4.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bubble3", "examples/new/wpp/bubble3.sp", "examples/new/wpp/bubble3.sk", BenchmarkInfo.UNDER),
                    new BenchmarkInfo("bubble4", "examples/new/wpp/bubble4.sp", "examples/new/wpp/bubble4.sk", BenchmarkInfo.UNDER)
            )
    );
    static List<BenchmarkInfo> specGameOver = new ArrayList<>();
    static List<BenchmarkInfo> specGameUnder = new ArrayList<>();

    final static String resultDir = "./result/";

    public RunBenchmarksMain() {
    }

    public static void main(String[] arguments) {
        final RunBenchmarksMain runBenchmarksMain = new RunBenchmarksMain();
//        runBenchmarksMain.writeCSV("NondeterUnder", specNondeterUnder);
        runBenchmarksMain.writeCSV("NondeterOver", specNondeterOver);
//            runBenchmarksMain.writeCSV("ConcurrencyUnder", specConcurrencyUnder);

    }

    public void writeCSV(String fileName, List<BenchmarkInfo> info) {
        final RunBenchmarksMain runBenchmarksMain = new RunBenchmarksMain();

        try (FileWriter fwCSV = new FileWriter(resultDir + fileName + ".csv");
             FileWriter fwProperties = new FileWriter(resultDir + fileName + ".txt")) {

            fwCSV.write("Name, GrammarSize, Time, SoundnessTime, SoundnessNum, PrecisionTime, PrecisionNum, SynthesisTime, SynthesisNum, numHiddenWitness\n");

            for (BenchmarkInfo args : info) {
                System.out.println("Running " + args.getName());
                RunningResults results = runBenchmarksMain.run(args);

                fwCSV.write(String.format("%s, %s\n", args.getName(), results.toCSV()));
                fwCSV.flush();
                fwProperties.write(String.format("Benchmark %s:\n %s \n\n", args.getName(), results));
                fwProperties.flush();
                System.out.println(results.toCSV());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public RunningResults run(BenchmarkInfo args) {
        final Spyro spyroMain = new Spyro(args.toStringArray());
        RunningResults results;
        try {
            results = spyroMain.solve();
        } catch (SketchException e) {
            e.print();
            throw e;

        } catch (Error | RuntimeException e) {
            ErrorHandling.handleErr(e);
            throw e;
        }

        return results;
    }

}
