package mimingucci.baomau.exception;

public class AccountExistBeforeException extends Exception{
    public AccountExistBeforeException() {
    }

    public AccountExistBeforeException(String message) {
        super(message);
    }

    public AccountExistBeforeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExistBeforeException(Throwable cause) {
        super(cause);
    }

    public AccountExistBeforeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
