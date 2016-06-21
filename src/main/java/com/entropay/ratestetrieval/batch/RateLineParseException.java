package com.entropay.ratestetrieval.batch;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
public class RateLineParseException extends RuntimeException {

    private final String filename;
    private final String line;

    public RateLineParseException(String message, String filename, String line) {
        super(message);
        this.filename = filename;
        this.line = line;
    }

    public RateLineParseException(String message, Throwable cause, String filename, String line) {
        super(message, cause);
        this.filename = filename;
        this.line = line;
    }

    public String getFilename() {
        return filename;
    }

    public String getLine() {
        return line;
    }
}
