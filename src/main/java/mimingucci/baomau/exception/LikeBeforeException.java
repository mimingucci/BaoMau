package mimingucci.baomau.exception;

public class LikeBeforeException extends Exception{
    public LikeBeforeException() {
    }

    public LikeBeforeException(String message) {
        super(message);
    }

    public LikeBeforeException(String message, Throwable cause) {
        super(message, cause);
    }

    public LikeBeforeException(Throwable cause) {
        super(cause);
    }

    public LikeBeforeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
