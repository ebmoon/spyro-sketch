package spyro.util.exceptions;

/**
 * Exception from constructing sketch code from Spyro input
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class ResultExtractException extends SpyroException {
    private static final long serialVersionUID = 2318723876L;

    public ResultExtractException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Result Extracting Error";
    }
}
