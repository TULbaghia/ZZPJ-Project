package pl.lodz.p.it.zzpj.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private long tokenExpiration;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;


    }
}
