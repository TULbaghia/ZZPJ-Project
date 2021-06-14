package pl.lodz.p.it.zzpj.service.auth.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.exception.RegistrationException;
import pl.lodz.p.it.zzpj.service.auth.dto.RegisterAccountDto;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AccountService accountService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegisterAccountDto request) throws RegistrationException {
        String token = null;
        try {
            Account newAccount = new Account(request.getFirstName(), request.getLastName(), request.getEmail(),
                    request.getPassword(), AccountRole.USER);
            token = accountService.singUpUser(newAccount);
        } catch (IllegalStateException illegalStateException) {
            throw new RegistrationException(illegalStateException.getMessage());
        }
        return token;
    }

    @Transactional
    public String confirmToken(String token) throws IllegalStateException {
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
}
