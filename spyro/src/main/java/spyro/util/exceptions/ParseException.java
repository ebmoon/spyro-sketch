package spyro.util.exceptions;

import spyro.util.exceptions.SpyroException;

public class ParseException extends SpyroException {
    private static final long serialVersionUID = 32493250295264101L;

    public ParseException(String msg) {
        super(msg);
    }

    @Override
    protected String messageClass() {
        return "Program Parse Error";
    }
}
