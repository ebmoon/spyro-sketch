package spyro.util.exceptions;

import sketch.compiler.main.other.ErrorHandling;
import sketch.util.exceptions.SketchException;

import static sketch.util.DebugOut.printError;

/**
 * Exception for errors occurring during Spyro execution (not the underlying solver).
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class SpyroException extends SketchException {
    private static final long serialVersionUID = 495808246383L;

    public SpyroException(String msg) {
        super(msg);
    }

    public SpyroException(String msg, Throwable base) {
        super(msg, base);
    }

    @Override
    public void printInner(boolean message, boolean stacktrace, boolean program) {
        if (message) {
            if (showMessageClass()) {
                printError("[SPYRO]", this.messageClass() + ":", this.getMessage());
            } else {
                printError("[SPYRO]", this.getMessage());
            }
            if (this.getCause() != null) {
                printError("    Caused by: ", this.getCause().getMessage());
            }
        }
        if (stacktrace) {
            dumpStackTraceToFile(this.getStackTrace());
        }
        if (program && lastGoodProg != null) {
            ErrorHandling.dumpProgramToFile(lastGoodProg);
        }
        subclassPrintEnd();
    }
}
