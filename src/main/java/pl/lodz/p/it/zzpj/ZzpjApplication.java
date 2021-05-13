package pl.lodz.p.it.zzpj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.lodz.p.it.zzpj.jwt.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class ZzpjApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzpjApplication.class, args);
    }

}
