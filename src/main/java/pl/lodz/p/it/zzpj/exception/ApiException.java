package pl.lodz.p.it.zzpj.exception;

public class ApiException extends AppBaseException {
    private static final String API_EXCEPTION = "exception.execution_error";

    public ApiException() {
        super(API_EXCEPTION);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
