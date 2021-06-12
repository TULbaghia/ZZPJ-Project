package pl.lodz.p.it.zzpj.exception;

public class RegistrationException extends AppBaseException{
    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
