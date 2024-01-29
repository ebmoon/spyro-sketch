package spyro.util.exceptions;

/**
 * Exception from constructing sketch code from Spyro input
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class SketchConversionException extends SpyroException {
    private static final long serialVersionUID = 6075166005096891208L;

    public SketchConversionException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Sketch Query Construction Error";
    }
}
