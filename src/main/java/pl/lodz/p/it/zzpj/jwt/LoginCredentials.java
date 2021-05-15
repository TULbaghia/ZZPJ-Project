package pl.lodz.p.it.zzpj.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.zzpj.validators.Email;
import pl.lodz.p.it.zzpj.validators.Password;

@NoArgsConstructor
@Getter
@Setter
public class LoginCredentials {
    @Email
    private String email;
    @Password
    private String password;
}
