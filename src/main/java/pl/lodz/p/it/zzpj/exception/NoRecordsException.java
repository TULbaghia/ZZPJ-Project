package pl.lodz.p.it.zzpj.exception;

public class NoRecordsException extends AppBaseException {
    private static final String NO_RECORD = "exception.record_not_exists";

    public NoRecordsException() {
        super(NO_RECORD);
    }

    public NoRecordsException(String message) {
        super(message);
    }

    public NoRecordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRecordsException(Throwable cause) {
        super(cause);
    }
}
