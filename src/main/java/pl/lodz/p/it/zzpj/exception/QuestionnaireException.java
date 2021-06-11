package pl.lodz.p.it.zzpj.exception;

public class QuestionnaireException extends AppBaseException {
    public QuestionnaireException() {
        super();
    }

    public QuestionnaireException(String message) {
        super(message);
    }

    public QuestionnaireException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionnaireException(Throwable cause) {
        super(cause);
    }
}
