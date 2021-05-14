package pl.lodz.p.it.zzpj.jwt;

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
