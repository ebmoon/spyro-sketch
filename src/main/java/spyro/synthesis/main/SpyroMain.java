package spyro.synthesis.main;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Parameter;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.StmtBlock;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.passes.SubstituteSolution;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.util.Pair;
import sketch.util.exceptions.SketchException;
import sketch.util.exceptions.SketchNotResolvedException;
import spyro.compiler.ast.Query;
import spyro.compiler.main.cmdline.SpyroOptions;
import spyro.compiler.parser.BuildAstVisitor;
import spyro.compiler.parser.SpyroLexer;
import spyro.compiler.parser.SpyroParser;
import spyro.synthesis.Example;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;
import spyro.synthesis.PropertySet;
import spyro.synthesis.primitives.*;
import spyro.util.exceptions.OutputParseException;
import spyro.util.exceptions.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

/**
 * The main entry point for the Spyro specification synthesizer.
 * Running it as a standalone program reads a list of files provided
 * on the command line. It considers the first file as Spyro file, and
 * remaining as sketch program files.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SpyroMain extends SequentialSketchMain {
    public static boolean isDebug = true;
    public SpyroOptions options;

    private Program prog;
    private Query query;
    private CommonSketchBuilder commonSketchBuilder;
    private SynthesisSketchBuilder synth;
    private SoundnessSketchBuilder soundness;
    private PrecisionSketchBuilder precision;
    private ImprovementSketchBuilder improvement;

    private Property truth;

    public SpyroMain(String[] args) {
        super(new SpyroOptions(args));
        this.options = (SpyroOptions) super.options;

        PlatformLocalization.getLocalization().setTempDirs();
        if (!isDebug)
            redirectStderrToNull();
    }

    void redirectStderrToNull() {
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // DO NOTHING
            }
        }));
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
        prog = preprocAndSemanticCheck(prog);
        return partialEvalAndSolve(prog);
    }

    private Program simplifySynthResult(SynthesisResult synthResult) {
        Program finalCleaned = synthResult.lowered.highLevelC;
        Program substituted = (new SubstituteSolution(varGen, options, synthResult.solution))
                .visitProgram(finalCleaned);
        return (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
                .visitProgram(substituted);
    }

    public Example checkSoundness(Property phi) {
        if (isDebug)
            System.out.println("CheckSoundness");
        Program sketchCode = soundness.soundnessSketchCode(phi);

        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                return new SoundnessResultExtractor(substitutedCleaned).extractPositiveExample();
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }
    }

    public Property synthesize(ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.println("Synthesize");
        Program sketchCode = synth.synthesisSketchCode(pos, neg);

        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                return new SynthesisResultExtractor(substitutedCleaned).extractProperty();
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }
    }

    public Pair<Property, Example> checkPrecision(PropertySet psi, Property phi, ExampleSet pos, ExampleSet neg) {
        if (isDebug)
            System.out.println("CheckPrecision");
        Program sketchCode = precision.precisionSketchCode(psi, phi, pos, neg);

        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                PrecisionResultExtractor extractor = new PrecisionResultExtractor(substitutedCleaned);
                return new Pair(extractor.extractProperty(), extractor.extractNegativeExample());
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }
    }

    public Example checkImprovement(PropertySet psi, Property phi) {
        if (isDebug)
            System.out.println("CheckImprovement");
        Program sketchCode = improvement.improvementSketchCode(psi, phi);

        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                ImprovementResultExtractor extractor = new ImprovementResultExtractor(substitutedCleaned);
                return extractor.extractNegativeExample();
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

    public PropertySet synthesizeProperties(PropertySet psiInit) {
        PropertySet psi = new PropertySet(commonSketchBuilder);
        ExampleSet pos = new ExampleSet();
        ExampleSet negMust;
        Property phi;
        PropertySynthesisResult result;

        psi.addAll(psiInit.getProperties());

        // TODO Implement Loop
        while (true) {
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

            result = synthesizeProperty(new PropertySet(commonSketchBuilder), phi, pos, negMust);
            phi = result.prop;
            pos = result.pos;
            psi.add(phi);
        }
    }

    public void run() {
        try {
            query = parseSpyroQuery();
            prog = parseProgram();
        } catch (RecognitionException | TokenStreamException | IOException e) {
            throw new ParseException("could not parse program");
        }

        commonSketchBuilder = new CommonSketchBuilder(prog);
        query.accept(commonSketchBuilder);

        synth = new SynthesisSketchBuilder(commonSketchBuilder);
        soundness = new SoundnessSketchBuilder(commonSketchBuilder);
        precision = new PrecisionSketchBuilder(commonSketchBuilder);
        improvement = new ImprovementSketchBuilder(commonSketchBuilder);

        List<Parameter> params = commonSketchBuilder.getExtendedParams("out");
        truth = Property.truth(params);

        PropertySet psi = new PropertySet(commonSketchBuilder);
        PropertySet properties = synthesizeProperties(psi);

        int idx = 0;
        for (Property prop : properties.getProperties()) {
            System.out.println(idx++);
            System.out.println(prop.toSketchCode().getBody().toString());
        }
    }

    static class PropertySynthesisResult {
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