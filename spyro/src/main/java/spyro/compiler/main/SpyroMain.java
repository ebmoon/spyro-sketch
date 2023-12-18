package spyro.compiler.main;

import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import java.io.FileInputStream;
import java.io.IOException;

import sketch.compiler.main.other.ErrorHandling;
import sketch.util.exceptions.SketchException;
import spyro.compiler.main.cmdline.SpyroOptions;
import spyro.compiler.ast.*;
import spyro.compiler.parser.*;
import spyro.util.exceptions.ParseException;

public class SpyroMain implements Runnable
{	
	public SpyroOptions options;
	
	public SpyroMain(String[] args) {
		this.options = new SpyroOptions(args);
//		SpyroCommandParser parser = new SpyroCommandParser();
//		CommandLine line = parser.parse(args);
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
		try {
			Query query = parseFiles();
			
			System.out.println(query);
		} catch (RecognitionException | TokenStreamException | IOException e) {
			throw new ParseException("could not parse program");
		}
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
