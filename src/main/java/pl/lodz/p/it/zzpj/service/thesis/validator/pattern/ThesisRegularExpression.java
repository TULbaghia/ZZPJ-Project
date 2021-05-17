package pl.lodz.p.it.zzpj.service.thesis.validator.pattern;

public class ThesisRegularExpression {
    public static final String WORD = "[A-Za-z][a-z\\-]*";
    public static final String DOI = "10.\\d{4,9}\\/[-._;()\\/:a-z0-9A-Z]+";
    public static final String TOPIC = "[A-Z][a-z]+";
}
