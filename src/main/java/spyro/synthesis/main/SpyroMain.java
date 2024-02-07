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
import spyro.synthesis.main.cmdline.SpyroOptions;
import spyro.compiler.parser.BuildAstVisitor;
import spyro.compiler.parser.SpyroLexer;
import spyro.compiler.parser.SpyroParser;
import spyro.synthesis.Example;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;
import spyro.synthesis.PropertySet;
import spyro.synthesis.primitives.*;
import spyro.util.exceptions.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main entry point for the Spyro specification synthesizer.
 * Running it as a standalone program reads a list of files provided
 * on the command line. It considers the first file as Spyro file, and
 * remaining as sketch program files.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SpyroMain extends SequentialSketchMain {
    public static boolean isDebug = false;
    public boolean isVerbose;
    public SpyroOptions options;

    private CommonSketchBuilder commonSketchBuilder;
    private SynthesisSketchBuilder synth;
    private MinimizationSynthesisSketchBuilder synthMin;
    private SoundnessSketchBuilder soundness;
    private PrecisionSketchBuilder precision;
    private PrecisionSketchBuilder precisionMin;
    private ImprovementSketchBuilder improvement;
    private Map<String, Function> lambdaFunctions;

    private int innerIterator = 0;
    private int outerIterator = 0;

    private final static String tempFileDir = "tmp";

    private Property truth;
    final private PrintStream oldErr = System.err;

    public SpyroMain(String[] args) {
        super(new SpyroOptions(args));
        this.options = (SpyroOptions) super.options;
        isVerbose = this.options.debugOpts.verbosity > 1;

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

    void restoreStderr() {
        System.setErr(oldErr);
    }

    String getNewTempFilePath() {
        return tempFileDir + String.format("/%d_%d.sk", outerIterator, innerIterator);
    }

    public static void main(String[] args) {
        final SpyroMain spyroMain = new SpyroMain(args);
        long beg = System.currentTimeMillis();
        int exitCode = 0;
        try {
            spyroMain.run();
        } catch (SketchException e) {
            e.print();
            if (isDebug) {
                throw e;
            } else {
                exitCode = 1;
            }
        } catch (java.lang.Error e) {
            ErrorHandling.handleErr(e);
            if (isDebug) {
                throw e;
            } else {
                exitCode = 1;
            }
        } catch (RuntimeException e) {
            ErrorHandling.handleErr(e);
            if (isDebug) {
                throw e;
            } else {
                exitCode = 1;
            }
        } finally {
            System.out.println("Total time = " + (System.currentTimeMillis() - beg));
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
        if (!isVerbose)
            redirectStderrToNull();
        prog = preprocAndSemanticCheck(prog);
        SynthesisResult result = partialEvalAndSolve(prog);
        if (!isVerbose)
            restoreStderr();
        innerIterator++;
        if (options.debugOpts.dumpSketch) {
            File path = new File(getNewTempFilePath());
            prog.debugDump(path);
        }
        return result;
    }

    private Program simplifySynthResult(SynthesisResult synthResult) {
        Program finalCleaned = synthResult.lowered.highLevelC;
        Program substituted = (new SubstituteSolution(varGen, options, synthResult.solution))
                .visitProgram(finalCleaned);
        return (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
                .visitProgram(substituted);
    }

    public Example checkSoundness(Property phi) {
        if (isVerbose)
            System.out.printf("CheckSoundness : Property %d - Query %d\n",outerIterator,innerIterator);
        Program sketchCode = soundness.soundnessSketchCode(phi, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                return ResultExtractor.extractPositiveExample(substitutedCleaned);
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }
    }

    public Property synthesize(ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.printf("Synthesize : Property %d - Query %d)\n",outerIterator,innerIterator);
        Program sketchCode = synth.synthesisSketchCode(pos, neg, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
                return ResultExtractor.extractProperty(substitutedCleaned);
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }


    }

    public Property synthesizeMin(ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.printf("Synthesize (Minimize formula) : Property %d - Query %d\n",outerIterator,innerIterator);
        Program sketchCode = synthMin.synthesisSketchCode(pos, neg, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
                return ResultExtractor.extractProperty(substitutedCleaned);
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }


    }

    public Pair<Property, Example> checkPrecision(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.printf("CheckPrecision : Property %d - Query %d\n",outerIterator,innerIterator);
        Program sketchCode = precision.precisionSketchCode(psi, phi, pos, neg, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                lambdaFunctions = ResultExtractor.extractLambdaFunctions(substitutedCleaned);
                return new Pair(
                        ResultExtractor.extractProperty(substitutedCleaned),
                        ResultExtractor.extractNegativeExamplePrecision(substitutedCleaned));
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }

    }

    public Pair<Property, Example> checkPrecisionMin(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.printf("CheckPrecision (Minimize formula) : Property %d - Query %d\n",outerIterator,innerIterator);
        Program sketchCode = precisionMin.precisionSketchCode(psi, phi, pos, neg, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                lambdaFunctions.putAll(ResultExtractor.extractLambdaFunctions(substitutedCleaned));
                return new Pair(
                        ResultExtractor.extractProperty(substitutedCleaned),
                        ResultExtractor.extractNegativeExamplePrecision(substitutedCleaned));
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }

    }

    public Example checkImprovement(PropertySet psi, Property phi) {
        if (isDebug)
            System.out.printf("CheckImprovement : Property %d - Query %d\n",outerIterator,innerIterator);
        Program sketchCode = improvement.improvementSketchCode(psi, phi, lambdaFunctions.values());
        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                return ResultExtractor.extractNegativeExampleImprovement(substitutedCleaned);
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
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

    public PropertySet synthesizeProperties(PropertySet psiInit) {
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
            negMust = result.negMust;

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

            // Strengthen the found property to be most precise L-property
            result = synthesizeMinimalProperty(new PropertySet(commonSketchBuilder), phi, pos, negMust);
            phi = result.prop;
            pos = result.pos;
            psi.add(phi);
            outerIterator ++;
            innerIterator =0;
        }
    }

    public void run() {
        Program prog;
        Query query;
        try {
            query = parseSpyroQuery();
            prog = parseProgram();
        } catch (RecognitionException | TokenStreamException | IOException e) {
            throw new ParseException("could not parse program");
        }

        commonSketchBuilder = new CommonSketchBuilder(prog);
        MinimizationSketchBuilder minSketchBuilder = new MinimizationSketchBuilder(prog);
        query.accept(commonSketchBuilder);
        query.accept(minSketchBuilder);

        synth = new SynthesisSketchBuilder(commonSketchBuilder);
        soundness = new SoundnessSketchBuilder(commonSketchBuilder);
        precision = new PrecisionSketchBuilder(synth);
        synthMin = new MinimizationSynthesisSketchBuilder(minSketchBuilder);
        precisionMin = new PrecisionSketchBuilder(synthMin);
        improvement = new ImprovementSketchBuilder(commonSketchBuilder);

        lambdaFunctions = new HashMap<>();

        List<Parameter> params = commonSketchBuilder.getExtendedParams(Property.outputVarID, false);
        truth = Property.truth(params);

        PropertySet psi = new PropertySet(commonSketchBuilder);
        PropertySet properties = synthesizeProperties(psi);

        int idx = 0;
        for (Property prop : properties.getProperties()) {
            String code = prop.toSketchCode().getBody().toString();

            System.out.println("Property " + idx++);
            System.out.println(code);

            for (Map.Entry<String, Function> entry : lambdaFunctions.entrySet()) {
                String key = entry.getKey();
                if (code.contains(key)) {
                    System.out.println(entry.getValue());
                    System.out.println(entry.getValue().getBody().toString());
                }
            }
        }
    }

    private static class PropertySynthesisResult {
        Property prop;
        ExampleSet pos;
        ExampleSet negMust;

        public PropertySynthesisResult(Property prop, ExampleSet pos, ExampleSet negMust) {
            this.prop = prop;
            this.pos = pos;
            this.negMust = negMust;
        }
    }
}