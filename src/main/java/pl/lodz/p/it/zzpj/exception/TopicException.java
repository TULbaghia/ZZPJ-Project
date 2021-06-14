package pl.lodz.p.it.zzpj.exception;

public class TopicException extends AppBaseException {
    private static final String TOPIC_EXISTS = "exception.topic.already_exists";

    public TopicException() {
        super();
    }

    public TopicException(String message) {
        super(message);
    }

    public TopicException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicException(Throwable cause) {
        super(cause);
    }

    public static TopicException topicAlreadyExists(Throwable cause) {
        return new TopicException(TOPIC_EXISTS, cause);
    }
}
