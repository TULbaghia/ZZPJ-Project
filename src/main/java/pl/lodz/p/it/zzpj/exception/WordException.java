package pl.lodz.p.it.zzpj.exception;

public class WordException extends AppBaseException {
    private static final String ALREADY_BANNED = "exception.word_already_useless";

    public WordException() {
        super();
    }

    public WordException(String message) {
        super(message);
    }

    public WordException(String message, Throwable cause) {
        super(message, cause);
    }

    public WordException(Throwable cause) {
        super(cause);
    }

    public static WordException alreadyBanned() {
        return new WordException(ALREADY_BANNED);
    }
}
