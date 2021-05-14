package pl.lodz.p.it.zzpj.exception;

public class NoRecordsException extends AppBaseException {


    public NoRecordsException() {
        super();
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
