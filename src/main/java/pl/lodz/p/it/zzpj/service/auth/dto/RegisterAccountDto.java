package pl.lodz.p.it.zzpj.service.auth.dto;

import lombok.*;
import pl.lodz.p.it.zzpj.service.auth.validator.Email;
import pl.lodz.p.it.zzpj.service.auth.validator.Firstname;
import pl.lodz.p.it.zzpj.service.auth.validator.Lastname;
import pl.lodz.p.it.zzpj.service.auth.validator.Password;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterAccountDto {

    @Firstname
    private final String firstName;
    @Lastname
    private final String lastName;
    @Email
    private final String email;
    @Password
    private final String password;
}
