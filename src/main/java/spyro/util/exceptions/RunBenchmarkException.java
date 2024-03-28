package spyro.util.exceptions;

/**
 * Exception from constructing sketch code from Spyro input
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class RunBenchmarkException extends SpyroException {
    private static final long serialVersionUID = 10934506189029L;

    public RunBenchmarkException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Benchmark Running Error";
    }
}
