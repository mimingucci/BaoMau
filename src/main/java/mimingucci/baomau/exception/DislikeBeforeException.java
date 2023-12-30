package mimingucci.baomau.exception;

public class DislikeBeforeException extends Exception{
    public DislikeBeforeException() {
    }

    public DislikeBeforeException(String message) {
        super(message);
    }

    public DislikeBeforeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DislikeBeforeException(Throwable cause) {
        super(cause);
    }

    public DislikeBeforeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
