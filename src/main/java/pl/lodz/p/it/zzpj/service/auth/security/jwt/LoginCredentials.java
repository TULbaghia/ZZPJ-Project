package pl.lodz.p.it.zzpj.service.auth.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.zzpj.service.auth.validator.Email;
import pl.lodz.p.it.zzpj.service.auth.validator.Password;

@NoArgsConstructor
@Getter
@Setter
public class LoginCredentials {
    @Email
    private String email;
    @Password
    private String password;
}
