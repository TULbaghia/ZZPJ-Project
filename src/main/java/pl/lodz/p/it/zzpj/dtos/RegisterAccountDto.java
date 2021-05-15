package pl.lodz.p.it.zzpj.dtos;

import lombok.*;
import pl.lodz.p.it.zzpj.validators.Password;

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
