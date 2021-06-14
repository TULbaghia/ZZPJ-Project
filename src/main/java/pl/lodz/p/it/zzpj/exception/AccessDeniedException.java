package pl.lodz.p.it.zzpj.exception;

public class AccessDeniedException extends AppBaseException {
    private static final String ACCESS_DENIED = "exception.forbidden";

    public AccessDeniedException() {
        super(ACCESS_DENIED);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }
}
