package pl.lodz.p.it.zzpj.managers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.dtos.RegisterAccountDto;
import pl.lodz.p.it.zzpj.email.EmailSender;
import pl.lodz.p.it.zzpj.entities.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.entities.user.Account;
import pl.lodz.p.it.zzpj.entities.user.AccountRole;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AccountService accountService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegisterAccountDto request) {
        String token = accountService
                .singUpUser(new Account(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getPassword(), AccountRole.USER));

        String link = "http://localhost:8080/api/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), getEmailContent(request.getFirstName(), link));
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
