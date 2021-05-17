package pl.lodz.p.it.zzpj.service.auth.dto;

import lombok.*;
import pl.lodz.p.it.zzpj.service.auth.validator.Password;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterAccountDto {

    private final String firstName;
    private final String lastName;
    private final String email;
    @Password
    private final String password;
}
