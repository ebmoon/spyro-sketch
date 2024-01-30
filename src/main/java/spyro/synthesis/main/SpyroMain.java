package spyro.synthesis.main;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import java.io.FileInputStream;
import java.io.IOException;

import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.stencilSK.EliminateStarStatic;
import sketch.util.exceptions.SketchException;
import spyro.compiler.main.cmdline.SpyroOptions;
import spyro.compiler.ast.*;
import spyro.compiler.parser.*;
import spyro.synthesis.Example;
import spyro.synthesis.ExampleSet;
import spyro.synthesis.Property;
import spyro.synthesis.PropertySet;
import spyro.synthesis.primitives.CommonSketchBuilder;
import spyro.synthesis.primitives.PrecisionSketchBuilder;
import spyro.synthesis.primitives.SoundnessSketchBuilder;
import spyro.synthesis.primitives.SynthesisSketchBuilder;
import spyro.util.exceptions.ParseException;

/**
 * The main entry point for the Spyro specification synthesizer.
 * Running it as a standalone program reads a list of files provided
 * on the command line. It considers the first file as Spyro file, and 
 * remaining as sketch program files.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SpyroMain extends SequentialSketchMain
{	
	public SpyroOptions options;

    private SynthesisSketchBuilder synth;
    private SoundnessSketchBuilder soundness;
    private PrecisionSketchBuilder precision;
	
	public SpyroMain(String[] args) {
		super(new SpyroOptions(args));
		this.options = (SpyroOptions) super.options;
        PlatformLocalization.getLocalization().setTempDirs();
	}
	
    private Query parseSpyroQuery() throws java.io.IOException,
    	antlr.RecognitionException, antlr.TokenStreamException
	{
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

    public Example checkSoundness(Property phi) {
        // TODO Implement
        return null;
    }

    public Property synthesize(ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        // TODO Implement
        return null;
    }

    public Example checkPrecision(Property phi, ExampleSet pos, ExampleSet negMust, ExampleSet negMay, Property phiPrime) {
        // TODO Implement
        return null;
    }
    public Property synthesizeProperty(Property phiInit, ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        Property phiE = phiInit;
        Property phiLastSound = null;

        while(true) {
            Example ePos = checkSoundness(phiE);
            if (ePos != null) {
                pos.add(ePos);
                Property phiPrime = synthesize(pos, negMust, negMay);
                if (phiPrime != null) {
                    phiE = phiPrime;
                }
                else {
                    phiE = phiLastSound;
                    negMay.clear();
                }
            }
            else {
                negMust.merge(negMay);
                negMay.clear();
                phiLastSound = phiE;
                Property phiPrime = null;
                Example eNeg = checkPrecision(phiE, pos, negMust, negMay, phiPrime);
                if (eNeg == null) {
                    return phiE;
                }
                else {
                    negMay.add(eNeg);
                    phiE = phiPrime;
                }
            }
        }
    }

    public PropertySet synthesizeProperties(Property phiInit) {
        // TODO Implement
        return null;
    }

	public void run() {
    	Program prog = null;	// Sketch program
    	Query query = null;		// Spyro query
		try {
			query = parseSpyroQuery();
			prog = parseProgram();
		} catch (RecognitionException | TokenStreamException | IOException e) {
			throw new ParseException("could not parse program");
		}

        CommonSketchBuilder commonBuilder = new CommonSketchBuilder(prog);
        query.accept(commonBuilder);

        synth = new SynthesisSketchBuilder(commonBuilder);
        soundness = new SoundnessSketchBuilder(commonBuilder);
        precision = new PrecisionSketchBuilder(commonBuilder);

        synthesizeProperties(null);
	}
	
	public static boolean isDebug = true;
	
    public static void main(String[] args)
    {
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
}
