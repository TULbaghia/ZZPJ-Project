package pl.lodz.p.it.zzpj.service.auth.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginCredentials {

    private String username;
    private String password;

}
