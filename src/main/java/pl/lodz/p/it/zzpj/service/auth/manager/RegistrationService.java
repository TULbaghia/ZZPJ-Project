package pl.lodz.p.it.zzpj.service.auth.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.service.auth.dto.RegisterAccountDto;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AccountService accountService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegisterAccountDto request) {
        String token = accountService
                .singUpUser(new Account(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getPassword(), AccountRole.USER));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        accountService.enableUser(confirmationToken.getAccount().getEmail());
        return "confirmed";
    }

    private String getEmailContent(String name, String link) {
        return new StringBuilder()
                .append("<p style=\"Margin:0 0 0px 0;font-size:19px;line-height:25px;color:#0b0c0c\">")
                .append("Hi ")
                .append(name)
                .append(",</p>")
                .append("<br/>")
                .append("thank you for registering please click on the link to activate your account: ")
                .append("<a href=")
                .append(link)
                .append(">Activate Now</a>")
                .toString();
    }
}
