package spyro.util.exceptions;

/**
 * Exception from parsing sketch output
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class OutputParseException extends SpyroException {
    private static final long serialVersionUID = 5725610527291503461L;

    public OutputParseException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Sketch Output Parse Error";
    }
}

