package spyro.compiler.main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import sketch.compiler.main.other.ErrorHandling;
import sketch.util.exceptions.SketchException;
import spyro.compiler.main.cmdline.SpyroOptions;

public class SpyroMain implements Runnable
{	
	public SpyroMain(String[] args) {
//		SpyroCommandParser parser = new SpyroCommandParser();
//		CommandLine line = parser.parse(args);
	}
	
	public void run() {
//		parser.parse(args);
		
        System.out.println("Hello World!");
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
