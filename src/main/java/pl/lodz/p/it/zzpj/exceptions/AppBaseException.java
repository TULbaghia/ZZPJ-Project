package pl.lodz.p.it.zzpj.exceptions;

public class AppBaseException extends Exception {
    public AppBaseException() {
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppBaseException(Throwable cause) {
        super(cause);
    }
}
