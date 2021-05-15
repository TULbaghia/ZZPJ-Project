package pl.lodz.p.it.zzpj.validators;

public class RegularExpression {
    public static final String WORD = "[A-Za-z][a-z\\-]*";
    public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String ROLE = "Client | Admin";
}
