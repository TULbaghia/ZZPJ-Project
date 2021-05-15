package pl.lodz.p.it.zzpj.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.jwt.LoginCredentials;

@RequestMapping(path = "login")
@RestController
public class LoginController {

    @PostMapping
    public void login(@RequestBody LoginCredentials loginCredentials) {

    }
}
