package pl.lodz.p.it.zzpj.exception;

public class QuestionnaireException extends AppBaseException {
    private static final String QUESTIONNAIRE_EXCEPTION = "exception.questionnaire";
    private static final String QUESTIONNAIRE_LAST_NOT_SOLVED = "exception.questionnaire.last_not_solved";

    public QuestionnaireException() {
        super(QUESTIONNAIRE_EXCEPTION);
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

    public static QuestionnaireException lastQuestionnaireNotSolved() {
        return new QuestionnaireException(QUESTIONNAIRE_LAST_NOT_SOLVED);
    }
}
