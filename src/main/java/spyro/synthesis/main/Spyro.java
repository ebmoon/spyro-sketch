package spyro.synthesis.main;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.util.Pair;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;
import spyro.compiler.ast.Query;
import spyro.compiler.parser.BuildAstVisitor;
import spyro.compiler.parser.SpyroLexer;
import spyro.compiler.parser.SpyroParser;
import spyro.synthesis.*;
import spyro.synthesis.main.cmdline.SpyroOptions;
import spyro.synthesis.primitives.*;
import spyro.util.exceptions.ParseException;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

/**
 * The main entry point for the Spyro specification synthesizer.
 * Running it as a standalone program reads a list of files provided
 * on the command line. It considers the first file as Spyro file, and
 * remaining as sketch program files.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class Spyro extends SequentialSketchMain {
    public static boolean isDebug = false;
    public boolean isVerbose;
    public boolean isSketchVerbose;
    public SpyroOptions options;

    private CommonSketchBuilder commonSketchBuilder;
    private SynthesisSketchBuilder synth;
    private MinimizationSynthesisSketchBuilder synthMin;
    private SoundnessOverSketchBuilder soundness;
    private SoundnessUnderSketchBuilder soundnessUnder;
    private PrecisionOldOverSketchBuilder precisionOld;
    private PrecisionOldOverSketchBuilder precisionOldMin;
    private PrecisionOverSketchBuilder precisionOver;
    private PrecisionOverSketchBuilder precisionOverMin;
    private PrecisionUnderSketchBuilder precisionUnder;
    private PrecisionUnderSketchBuilder precisionUnderMin;
    private ImprovementSketchBuilder improvement;
    private HiddenWitnessSketchBuilder hiddenWitness;
    private HiddenValueSet hSet;
    private Map<String, Function> lambdaFunctions;
    long timeSoundness;
    int numSoundness;
    long timePrecision;
    int numPrecision;
    long timeSynthesis;
    int numSynthesis;
    int maxHiddenSize;

    private int innerIterator = 0;
    private int outerIterator = 0;

    private final static String tempFileDir = "tmp";

    private Property truth, falsity;
    final private PrintStream oldErr = System.err;
    final private PrintStream oldOut = System.out;

    public Spyro(String[] args) {
        super(new SpyroOptions(args));
        this.options = (SpyroOptions) super.options;
        this.isVerbose = this.options.debugOpts.verbosity > 1;
        this.isSketchVerbose = this.options.debugOpts.verbosity > 2;
        if (isSketchVerbose)
            this.options.debugOpts.verbosity = this.options.debugOpts.verbosity - 1;

        PlatformLocalization.getLocalization().setTempDirs();
        Path tempPath = Paths.get(tempFileDir);
        if (!Files.isDirectory(tempPath)) {
            try {
                Files.createDirectory(tempPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void redirectStderrToNull() {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // DO NOTHING
            }
        }));
    }

    void redirectStdoutToNull() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // DO NOTHING
            }
        }));
    }

    void restoreStderr() {
        System.setErr(oldErr);
    }

    void restoreStdout() {
        System.setOut(oldOut);
    }

    String getNewTempFilePath() {
        return tempFileDir + String.format("/%d_%d.sk", outerIterator, innerIterator);
    }

    public static void main(String[] args) {
        final Spyro spyroMain = new Spyro(args);
//        long beg = System.currentTimeMillis();
        int exitCode = 0;
        try {
            spyroMain.solve();
        } catch (SketchException e) {
            e.print();
            if (isDebug) {
                throw e;
            } else {
                exitCode = 1;
            }
        } catch (Error | RuntimeException e) {
            ErrorHandling.handleErr(e);
            if (isDebug) {
                throw e;
            } else {
                exitCode = 1;
            }
        } finally {
//            System.out.println("Total time = " + (System.currentTimeMillis() - beg));
        }
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    private Query parseSpyroQuery() throws java.io.IOException,
            antlr.RecognitionException, antlr.TokenStreamException {
        CharStream in = CharStreams.fromStream(new FileInputStream(options.spyroFile));
        SpyroLexer lexer = new SpyroLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SpyroParser parser = new SpyroParser(tokens);

        BuildAstVisitor visitor = new BuildAstVisitor();
        Query query = visitor.visitParse(parser.parse());

        return query;
    }

    private SynthesisResult runSketchSolver(Program prog) {
        if (options.debugOpts.dumpSketch) {
            File path = new File(getNewTempFilePath());
            prog.debugDump(path);
        }

        prog = preprocAndSemanticCheck(prog);

        // Redirect error message to null from sketch-backend
        if (!isVerbose)
            redirectStderrToNull();

        if (!isSketchVerbose)
            redirectStdoutToNull();

        SynthesisResult result;
        try {
            result = partialEvalAndSolve(prog);
        } catch (SketchNotResolvedException e) {
            result = null;
        }

        // Restore stderr so that user can see spyro error message
        if (!isVerbose)
            restoreStderr();

        if (!isSketchVerbose)
            restoreStdout();

        innerIterator++;

        return result;
    }

    private Program simplifySynthResult(SynthesisResult synthResult) {
        Program finalCleaned = synthResult.lowered.highLevelC;
        Program substituted = (new SubstituteSolution(varGen, options, synthResult.solution))
                .visitProgram(finalCleaned);
        return (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
                .visitProgram(substituted);
    }

    private Program runAndSimplify(Program sketchCode) {
        SynthesisResult synthResult = runSketchSolver(sketchCode);
        if (synthResult != null && synthResult.solution != null) {
            return simplifySynthResult(synthResult);
        } else return null;
    }

    public Example checkSoundness(Property phi) {
        numSoundness++;
        long startTime = System.currentTimeMillis();
        if (isVerbose)
            System.out.printf("CheckSoundness : Property %d - Query %d\n", outerIterator, innerIterator);
        Program sketchCode = soundness.soundnessSketchCode(phi, lambdaFunctions.values());
        Program substitutedCleaned = runAndSimplify(sketchCode);
        Example ret = null;
        if (substitutedCleaned != null)
            ret = ResultExtractor.extractPositiveExample(substitutedCleaned);
        if (isVerbose) System.out.println(ret == null ? "Sound." : "Unsound.");
        timeSoundness += System.currentTimeMillis() - startTime;
        return ret;
    }

    public Example checkSoundnessUnder(Property phi) {
        numSoundness++;
        long startTime = System.currentTimeMillis();
        if (isVerbose)
            System.out.printf("CheckSoundnessUnder : Property %d - Query %d\n", outerIterator, innerIterator);

        if (options.synthOpts.noReuseHidden)
            hSet = new HiddenValueSet();
        Example ret = null;
        while (true) {
            Program synthCandidateSketchCode = soundnessUnder.soundnessUnderSketchCode(phi, hSet, lambdaFunctions.values());
            Program substitutedCleaned = runAndSimplify(synthCandidateSketchCode);
            if (substitutedCleaned != null) {
                Example negExCandidate = ResultExtractor.extractNegativeExampleCandidate(substitutedCleaned, SoundnessUnderSketchBuilder.soundnessUnderFunctionID);
                Program synthCounterExampleSketchCode = hiddenWitness.hiddenWitnessSketchCode(negExCandidate);
                substitutedCleaned = runAndSimplify(synthCounterExampleSketchCode);
                if (substitutedCleaned != null) {
                    HiddenValue hiddenValue = ResultExtractor.extractHiddenValue(substitutedCleaned, commonSketchBuilder);
                    hSet.add(hiddenValue);
                } else {
                    ret = negExCandidate;
                    break;
                }
            } else break;
        }
        maxHiddenSize = max(maxHiddenSize, hSet.getHiddenValues().size());
        if (isVerbose) System.out.println(ret == null ? "Sound." : "Unsound.");
        timeSoundness += System.currentTimeMillis() - startTime;
        return ret;
    }

    public Property synthesize(ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("Synthesize : Property %d - Query %d\n", outerIterator, innerIterator);
        return synthesize(pos, neg, synth);
    }

    public Property synthesizeMin(ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("Synthesize (Minimize formula) : Property %d - Query %d\n", outerIterator, innerIterator);
        return synthesize(pos, neg, synthMin);
    }

    public Property synthesize(ExampleSet pos, ExampleSet neg, SynthesisSketchBuilder synth) {
        numSynthesis++;
        long startTime = System.currentTimeMillis();
        Program sketchCode = synth.synthesisSketchCode(pos, neg, lambdaFunctions.values());
        Program substitutedCleaned = runAndSimplify(sketchCode);
        Property ret = null;
        if (substitutedCleaned != null) {
            lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
            ret = ResultExtractor.extractProperty(substitutedCleaned);
        }
        if (isVerbose) System.out.println(ret == null ? "Failed." : "Succeeded.");
        timeSynthesis += System.currentTimeMillis() - startTime;
        return ret;
    }

    public Pair<Property, Example> checkPrecision(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("CheckPrecision : Property %d - Query %d\n", outerIterator, innerIterator);
        if (options.synthOpts.over)
            return checkPrecisionOver(psi, phi, pos, neg, precisionOver);
        return checkPrecisionOld(psi, phi, pos, neg, precisionOld);
    }

    public Pair<Property, Example> checkPrecisionMin(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("CheckPrecision (Minimize formula) : Property %d - Query %d\n", outerIterator, innerIterator);
        if (options.synthOpts.over)
            return checkPrecisionOver(psi, phi, pos, neg, precisionOverMin);
        return checkPrecisionOld(psi, phi, pos, neg, precisionOldMin);
    }


    public Pair<Property, Example> checkPrecisionOld(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg, PrecisionOldOverSketchBuilder precision) {
        numPrecision++;
        long startTime = System.currentTimeMillis();
        Program sketchCode = precision.precisionSketchCode(psi, phi, pos, neg, lambdaFunctions.values());
        Program substitutedCleaned = runAndSimplify(sketchCode);
        Pair<Property, Example> ret = null;
        if (substitutedCleaned != null) {

            Map<String, Function> extractedLF = ResultExtractor.extractLambdaFunctions(substitutedCleaned);
            if (precision == precisionOldMin)
                lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
            else
                lambdaFunctions = ResultExtractor.extractLambdaFunctions(substitutedCleaned);
            ret = new Pair<Property, Example>(
                    ResultExtractor.extractProperty(substitutedCleaned),
                    ResultExtractor.extractNegativeExamplePrecision(substitutedCleaned));
        }
        if (isVerbose) System.out.println(ret == null ? "Precise." : "Not Precise.");
        timePrecision += System.currentTimeMillis() - startTime;
        return ret;
    }

    public Pair<Property, Example> checkPrecisionOver(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg, PrecisionOverSketchBuilder precisionOver) {
        numPrecision++;
        long startTime = System.currentTimeMillis();
        if (options.synthOpts.noReuseHidden)
            hSet = new HiddenValueSet();
        Pair<Property, Example> ret = null;
        while (true) {
            Program synthCandidateSketchCode = precisionOver.precisionOverSketchCode(psi, phi, pos, neg, hSet, lambdaFunctions.values());
            Program substitutedCleaned = runAndSimplify(synthCandidateSketchCode);
            if (substitutedCleaned != null) {
                Example negExCandidate = ResultExtractor.extractNegativeExampleCandidate(substitutedCleaned, PrecisionOverSketchBuilder.precisionOverFunctionID);
                Property propCandidate = ResultExtractor.extractProperty(substitutedCleaned);
                Map<String, Function> lfCandidate = ResultExtractor.extractLambdaFunctions(substitutedCleaned);

                Program synthCounterExampleSketchCode = hiddenWitness.hiddenWitnessSketchCode(negExCandidate);
                substitutedCleaned = runAndSimplify(synthCounterExampleSketchCode);
                if (substitutedCleaned != null) {

                    HiddenValue hiddenValue = ResultExtractor.extractHiddenValue(substitutedCleaned, commonSketchBuilder);
                    hSet.add(hiddenValue);
                } else {
                    if (precisionOver == precisionOverMin)
                        lambdaFunctions.putAll(lfCandidate);
                    else
                        lambdaFunctions = lfCandidate;
                    ret = new Pair<>(propCandidate, negExCandidate);
                    break;
                }
            } else break;
        }
        maxHiddenSize = max(maxHiddenSize, hSet.getHiddenValues().size());
        if (isVerbose) System.out.println(ret == null ? "Precise." : "Not Precise.");
        timePrecision += System.currentTimeMillis() - startTime;
        return ret;
    }


    public Pair<Property, Example> checkPrecisionUnder(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("CheckPrecisionUnder : Property %d - Query %d\n", outerIterator, innerIterator);
        return checkPrecisionUnder(psi, phi, pos, neg, precisionUnder);
    }

    public Pair<Property, Example> checkPrecisionUnderMin(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isVerbose)
            System.out.printf("CheckPrecisionUnder (Minimize formula): Property %d - Query %d\n", outerIterator, innerIterator);
        return checkPrecisionUnder(psi, phi, pos, neg, precisionUnderMin);
    }

    public Pair<Property, Example> checkPrecisionUnder(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg, PrecisionUnderSketchBuilder builder) {
        numPrecision++;
        long startTime = System.currentTimeMillis();
        Program sketchCode = builder.precisionUnderSketchCode(psi, phi, pos, neg, lambdaFunctions.values());
        Program substitutedCleaned = runAndSimplify(sketchCode);
        Pair<Property, Example> ret = null;
        if (substitutedCleaned != null) {
            if (builder == precisionUnderMin)
                lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
            else
                lambdaFunctions = ResultExtractor.extractLambdaFunctions(substitutedCleaned);
            ret = new Pair<Property, Example>(
                    ResultExtractor.extractProperty(substitutedCleaned),
                    ResultExtractor.extractPositiveExamplePrecisionUnder(substitutedCleaned));
        }
        if (isVerbose) System.out.println(ret == null ? "Precise." : "Not Precise.");
        timePrecision += System.currentTimeMillis() - startTime;
        return ret;
    }

    public Example checkImprovement(PropertySet psi, Property phi) {
        if (isVerbose)
            System.out.printf("CheckImprovement : Property %d - Query %d\n", outerIterator, innerIterator);
        Program sketchCode = improvement.improvementSketchCode(psi, phi, lambdaFunctions.values());
        Program substitutedCleaned = runAndSimplify(sketchCode);
        if (substitutedCleaned != null) {
            if (isVerbose)
                System.out.println("Improved.");
            return ResultExtractor.extractNegativeExampleImprovement(substitutedCleaned);
        } else {
            if (isVerbose)
                System.out.println("Not Improved.");
            return null;
        }
    }

    public PropertySynthesisResult synthesizeProperty(PropertySet psi, Property phiInit, ExampleSet pos, ExampleSet negMust) {
        Property phiE = phiInit;
        Property phiLastSound = null;
        ExampleSet neg = negMust.copy();

        while (true) {
            Example ePos = checkSoundness(phiE);
            if (ePos != null) {
                pos.add(ePos);
                Property phiPrime = synthesize(pos, neg);
                if (phiPrime != null) {
                    phiE = phiPrime;
                } else {
                    phiE = phiLastSound;
                    neg = negMust.copy();
                }
            } else {
                negMust = neg.copy();
                phiLastSound = phiE;
                Pair<Property, Example> precisionResult = checkPrecision(psi, phiE, pos, neg);
                if (precisionResult == null) {
                    return new PropertySynthesisResult(phiE, pos, negMust);
                } else {
                    neg.add(precisionResult.getSecond());
                    phiE = precisionResult.getFirst();
                }
            }
        }
    }

    public PropertySynthesisResult synthesizeUnderProperty(PropertySet psi, Property phiInit, ExampleSet posMust, ExampleSet neg) {
        Property phiE = phiInit;
        Property phiLastSound = null;
        ExampleSet pos = posMust.copy();

        while (true) {
            Example eNeg = checkSoundnessUnder(phiE);
            if (eNeg != null) {
                neg.add(eNeg);
                Property phiPrime = synthesize(pos, neg);
                if (phiPrime != null) {
                    phiE = phiPrime;
                } else {
                    phiE = phiLastSound;
                    pos = posMust.copy();
                }
            } else {
                posMust = pos.copy();
                phiLastSound = phiE;
                Pair<Property, Example> precisionResult = checkPrecisionUnder(psi, phiE, pos, neg);
                if (precisionResult == null) {
                    return new PropertySynthesisResult(phiE, posMust, neg);
                } else {
                    pos.add(precisionResult.getSecond());
                    phiE = precisionResult.getFirst();
                }
            }
        }
    }

    public PropertySynthesisResult synthesizeMinimalProperty(PropertySet psi, Property phiInit, ExampleSet pos, ExampleSet negMust) {
        Property phiE = phiInit;
        Property phiLastSound = null;
        ExampleSet neg = negMust.copy();

        while (true) {
            Example ePos = checkSoundness(phiE);
            if (ePos != null) {
                pos.add(ePos);
                Property phiPrime = synthesizeMin(pos, neg);
                if (phiPrime != null) {
                    phiE = phiPrime;
                } else {
                    phiE = phiLastSound;
                    neg = negMust.copy();
                }
            } else {
                negMust = neg.copy();
                phiLastSound = phiE;
                Pair<Property, Example> precisionResult = checkPrecisionMin(psi, phiE, pos, neg);
                if (precisionResult == null) {
                    return new PropertySynthesisResult(phiE, pos, negMust);
                } else {
                    neg.add(precisionResult.getSecond());
                    phiE = precisionResult.getFirst();
                }
            }
        }
    }

    public PropertySynthesisResult synthesizeMinimalUnderProperty(PropertySet psi, Property phiInit, ExampleSet posMust, ExampleSet neg) {
        Property phiE = phiInit;
        Property phiLastSound = null;
        ExampleSet pos = posMust.copy();

        while (true) {
            Example eNeg = checkSoundnessUnder(phiE);
            if (eNeg != null) {
                neg.add(eNeg);
                Property phiPrime = synthesizeMin(pos, neg);
                if (phiPrime != null) {
                    phiE = phiPrime;
                } else {
                    phiE = phiLastSound;
                    pos = posMust.copy();
                }
            } else {
                posMust = pos.copy();
                phiLastSound = phiE;
                Pair<Property, Example> precisionResult = checkPrecisionUnderMin(psi, phiE, pos, neg);
                if (precisionResult == null) {
                    return new PropertySynthesisResult(phiE, posMust, neg);
                } else {
                    pos.add(precisionResult.getSecond());
                    phiE = precisionResult.getFirst();
                }
            }
        }
    }

    public PropertySet synthesizeOverProperties(PropertySet psiInit) {
        PropertySet psi = new PropertySet(commonSketchBuilder);
        ExampleSet pos = new ExampleSet();
        ExampleSet negMust;
        Property phi;
        PropertySynthesisResult result;

        psi.addAll(psiInit.getProperties());

        while (true) {
            // Find a property improves conjunction as much as possible
            result = synthesizeProperty(psi, truth, pos, new ExampleSet());
            phi = result.prop;
            pos = result.pos;
            negMust = result.neg;

            // Check if most precise candidates improves property.
            // If negMust is nonempty, those examples are witness of improvement.
            if (negMust.isEmpty()) {
                Example neg = checkImprovement(psi, phi);
                if (neg != null) {
                    negMust = new ExampleSet(Collections.singletonList(neg));
                } else {
                    return psi;
                }
            }

            // Synthesize a new candidate, which is minimized
            // We can always synthesize a property here
            // because we already have an example satisfying pos and negMust
            phi = synthesizeMin(pos, negMust);

            // Strengthen the found property to be most precise L-over-approximation
            result = synthesizeMinimalProperty(new PropertySet(commonSketchBuilder), phi, pos, negMust);
            phi = result.prop;
            pos = result.pos;
            psi.add(phi);
            outerIterator++;
            innerIterator = 0;
        }
    }

    public PropertySet synthesizeUnderProperties(PropertySet psiInit) {
        PropertySet psi = new PropertySet(commonSketchBuilder);
        ExampleSet posMust;
        ExampleSet neg = new ExampleSet();
        Property phi;
        PropertySynthesisResult result;

        psi.addAll(psiInit.getProperties());

        while (true) {
            // Find a property improves conjunction as much as possible
            result = synthesizeUnderProperty(psi, falsity, new ExampleSet(), neg);
            phi = result.prop;
            posMust = result.pos;
            neg = result.neg;

            // Check if most precise candidates improves property.
            // If posMust is nonempty, those examples are witness of improvement.
            if (posMust.isEmpty()) {
                Example pos = checkImprovement(psi, phi);
                if (pos != null) {
                    posMust = new ExampleSet(Collections.singletonList(pos));
                } else {
                    return psi;
                }
            }

            // Synthesize a new candidate, which is minimized
            // We can always synthesize a property here
            // because we already have an example satisfying posMust and neg
            phi = synthesizeMin(posMust, neg);

            // Weaken the found property to be most precise L-under-approximation
            result = synthesizeMinimalUnderProperty(new PropertySet(commonSketchBuilder), phi, posMust, neg);
            phi = result.prop;
            neg = result.neg;
            psi.add(phi);
            outerIterator++;
            innerIterator = 0;
        }
    }

    public RunningResults solve() {
        long startTime = System.currentTimeMillis();

        Program prog;
        Query query;
        try {
            query = parseSpyroQuery();
            prog = parseProgram();
        } catch (RecognitionException | TokenStreamException | IOException e) {
            throw new ParseException("could not parse program");
        }

        GrammarSizeCalculator cal = new GrammarSizeCalculator();
        BigInteger grammarSize = cal.computeSize(query);

        if (options.debugOpts.printGrammarSize) {
            BigDecimal sizeDecimal = new BigDecimal(grammarSize);
            DecimalFormat df = new DecimalFormat("0.######E0");
            System.out.println("Grammar Size:");
            System.out.println(grammarSize);
            System.out.println(df.format(sizeDecimal));
            return null;
        }

        timeSoundness = 0;
        numSoundness = 0;
        timePrecision = 0;
        numPrecision = 0;
        timeSynthesis = 0;
        numSynthesis = 0;


        commonSketchBuilder = new CommonSketchBuilder(prog, options.synthOpts.under);
        MinimizationSketchBuilder minSketchBuilder = new MinimizationSketchBuilder(prog, options.synthOpts.under);
        query.accept(commonSketchBuilder);
        query.accept(minSketchBuilder);

        synth = new SynthesisSketchBuilder(commonSketchBuilder);
        synthMin = new MinimizationSynthesisSketchBuilder(minSketchBuilder);

        soundness = new SoundnessOverSketchBuilder(commonSketchBuilder);
        precisionOld = new PrecisionOldOverSketchBuilder(synth);
        precisionOldMin = new PrecisionOldOverSketchBuilder(synthMin);
        precisionOver = new PrecisionOverSketchBuilder(synth);
        precisionOverMin = new PrecisionOverSketchBuilder(synthMin);

        improvement = new ImprovementSketchBuilder(commonSketchBuilder);

        soundnessUnder = new SoundnessUnderSketchBuilder(commonSketchBuilder);
        hiddenWitness = new HiddenWitnessSketchBuilder(commonSketchBuilder);
        precisionUnder = new PrecisionUnderSketchBuilder(synth);
        precisionUnderMin = new PrecisionUnderSketchBuilder(synthMin);

        lambdaFunctions = new HashMap<>();

        hSet = new HiddenValueSet();

        List<Parameter> params = commonSketchBuilder.getVariableAsParams(CommonSketchBuilder.ONLY_VISIBLE, Property.outputVarID);
        truth = Property.truth(params);
        falsity = Property.falsity(params);

        PropertySet psi = new PropertySet(commonSketchBuilder);
        PropertySet properties = options.synthOpts.under ? synthesizeUnderProperties(psi) : synthesizeOverProperties(psi);

        long elapsedTime = System.currentTimeMillis() - startTime;
        RunningResults results = new RunningResults(options.synthOpts.under, properties, lambdaFunctions, grammarSize, elapsedTime, timeSoundness, numSoundness, timePrecision, numPrecision, timeSynthesis, numSynthesis, maxHiddenSize);

        if (!options.debugOpts.noDisplayResults)
            System.out.println(results);
        return results;
    }


    public class PropertySynthesisResult {
        Property prop;
        ExampleSet pos;
        ExampleSet neg;

        public PropertySynthesisResult(Property prop, ExampleSet pos, ExampleSet neg) {
            this.prop = prop;
            this.pos = pos;
            this.neg = neg;
        }
    }
}
