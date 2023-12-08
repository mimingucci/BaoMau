package mimingucci.baomau.exception;

public class ReviewExistBeforeException extends Exception{
    public ReviewExistBeforeException() {
    }

    public ReviewExistBeforeException(String message) {
        super(message);
    }

    public ReviewExistBeforeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviewExistBeforeException(Throwable cause) {
        super(cause);
    }

    public ReviewExistBeforeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
