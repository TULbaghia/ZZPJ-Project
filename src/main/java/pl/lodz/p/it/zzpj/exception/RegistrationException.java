package pl.lodz.p.it.zzpj.exception;

public class RegistrationException extends AppBaseException {
    private static final String REGISTRATION_EXCEPTION = "exception.registration";

    public RegistrationException() {
        super(REGISTRATION_EXCEPTION);
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
