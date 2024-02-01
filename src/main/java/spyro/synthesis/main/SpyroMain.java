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


    public SpyroMain(String[] args) {
        super(new SpyroOptions(args));
        this.options = (SpyroOptions) super.options;
        PlatformLocalization.getLocalization().setTempDirs();
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
        prog = this.preprocAndSemanticCheck(prog);
        return this.partialEvalAndSolve(prog);
    }

    private Program simplifySynthResult(SynthesisResult synthResult) {
        Program finalCleaned = synthResult.lowered.highLevelC;
        Program substituted = (new SubstituteSolution(varGen, options, synthResult.solution))
                .visitProgram(finalCleaned);
        return (new CleanupFinalCode(varGen, options, visibleRControl(finalCleaned)))
                .visitProgram(substituted);
    }

    private Function findFunction(Program prog, String name) {
        List<Function> funcs = prog.getPackages().get(0).getFuncs();
        for (Function func : funcs)
            if (func.getName().equals(name))
                return func;

        return null;
    }

    public Example checkSoundness(Property phi) {
        Program sketchCode = soundness.soundnessSketchCode(phi);

        try {
            SynthesisResult synthResult = runSketchSolver(sketchCode);
            if (synthResult.solution != null) {
                Program substitutedCleaned = simplifySynthResult(synthResult);
                Function f = findFunction(substitutedCleaned, SoundnessSketchBuilder.soundnessFunctionID);
                if (f == null) {
                    throw new OutputParseException("Failed to find function: "
                            + SoundnessSketchBuilder.soundnessFunctionID);
                } else {
                    return (new SoundnessResultExtractor((StmtBlock) f.getBody()))
                            .extractPositiveExample();
                }
            } else {
                return null;
            }
        } catch (SketchNotResolvedException e) {
            return null;
        }
    }

    public Property synthesize(ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        // TODO Implement
        return null;
    }

    public Example checkPrecision(Property phi, ExampleSet pos, ExampleSet negMust, ExampleSet negMay, Property phiPrime) {
        // TODO Implement
        return null;
    }

    public Property synthesizeProperty(Property phiInit, ExampleSet pos, ExampleSet negMust) {
        Property phiE = phiInit;
        Property phiLastSound = null;
        ExampleSet negMay = new ExampleSet();

        while (true) {
            Example ePos = checkSoundness(phiE);
            if (ePos != null) {
                pos.add(ePos);
                Property phiPrime = synthesize(pos, negMust, negMay);
                if (phiPrime != null) {
                    phiE = phiPrime;
                } else {
                    phiE = phiLastSound;
                    negMay.clear();
                }
            } else {
                negMust.merge(negMay);
                negMay.clear();
                phiLastSound = phiE;
                Property phiPrime = null;
                Example eNeg = checkPrecision(phiE, pos, negMust, negMay, phiPrime);
                if (eNeg == null) {
                    return phiE;
                } else {
                    negMay.add(eNeg);
                    phiE = phiPrime;
                }
            }
        }
    }

    public PropertySet synthesizeProperties(Property phiInit) {
        ExampleSet pos = new ExampleSet();
        ExampleSet negMust = new ExampleSet();
        PropertySet properties = new PropertySet(commonSketchBuilder);

        // TODO Implement Loop
        Property prop = synthesizeProperty(phiInit, pos, negMust);
        System.out.println(prop.toSketchCode().getBody().toString());

        return properties;
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

        List<Parameter> params = commonSketchBuilder.getExtendedParams("out");
        Property phiInit = Property.truth(params);
        synthesizeProperties(phiInit);

        synthesizeProperties(null);
    }
}
