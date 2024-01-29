package spyro.compiler.main;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import java.io.FileInputStream;
import java.io.IOException;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.main.PlatformLocalization;
import sketch.compiler.main.cmdline.SketchOptions;
import sketch.compiler.main.other.ErrorHandling;
import sketch.compiler.main.passes.CleanupFinalCode;
import sketch.compiler.main.seq.SequentialSketchMain;
import sketch.compiler.passes.printers.SimpleCodePrinter;
import sketch.compiler.stencilSK.EliminateStarStatic;
import sketch.util.exceptions.SketchException;
import spyro.compiler.main.cmdline.SpyroOptions;
import spyro.compiler.ast.*;
import spyro.compiler.parser.*;
import spyro.synthesizer.GeneratorBuilder;
import spyro.synthesizer.PrimitiveConstructor;
import spyro.synthesizer.Property;
import spyro.util.exceptions.ParseException;

import javax.sound.midi.SysexMessage;

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
	
	public SpyroMain(String[] args) {
		super(new SpyroOptions(args));
		this.options = (SpyroOptions) super.options;
        PlatformLocalization.getLocalization().setTempDirs();
	}
	
    private Query parseFiles() throws java.io.IOException, 
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
	
	public void run() {
    	Program prog = null;	// Sketch program
    	Query query = null;		// Spyro query
		try {
			query = parseFiles();
			prog = parseProgram();


            GeneratorBuilder builder = new GeneratorBuilder();
            query.accept(builder);
//            for (Function func : builder.getTypeGenerators()){
//                System.out.println((new SimpleCodePrinter()).visitFunction(func));
//            }
//            for (Function func : builder.getNonTerminalGenerators()){
//                System.out.println((new SimpleCodePrinter()).visitFunction(func));
//            }
            PrimitiveConstructor primitiveConstructor = new PrimitiveConstructor(builder, prog);

            Property prop = new Property(builder.appendToVariableAsParams("out"));
            Program testSoundness = primitiveConstructor.constructSoundnessSketchCode(prop);

            testSoundness.debugDump();
            prog = testSoundness;

		} catch (RecognitionException | TokenStreamException | IOException e) {
			throw new ParseException("could not parse program");
		}

        prog = this.preprocAndSemanticCheck(prog);

        SynthesisResult synthResult = this.partialEvalAndSolve(prog);
        prog = synthResult.lowered.result;

        Program finalCleaned = synthResult.lowered.highLevelC;

        Program substituted;
        if (synthResult.solution != null) {
            EliminateStarStatic eliminate_star = new EliminateStarStatic(synthResult.solution);
            substituted = (Program) finalCleaned.accept(eliminate_star);
        } else {
            substituted = finalCleaned;
        }

        Program substitutedCleaned =
                (new CleanupFinalCode(varGen, options,
                        visibleRControl(finalCleaned))).visitProgram(substituted);

        generateCode(substitutedCleaned);
        this.log(1, "[SKETCH] DONE");

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
