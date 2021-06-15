package pl.lodz.p.it.zzpj.service.auth.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lodz.p.it.zzpj.entity.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.exception.RegistrationException;
import pl.lodz.p.it.zzpj.service.auth.dto.RegisterAccountDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    RegistrationService registrationService;

    @Mock
    AccountService accountService;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Test
    public void registerTest() throws RegistrationException {
        RegisterAccountDto registerAccountDto = new RegisterAccountDto("Adam", "Adamski", "adam@adam.pl","password");
        Account account = new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER);

        given(accountService.singUpUser(account)).willReturn("token");
        assertEquals(registrationService.register(registerAccountDto), "token");
        verify(accountService, times(1)).singUpUser(account);
    }

    @Test
    public void confirmTokenNegative1Test() {
        given(confirmationTokenService.getToken(ArgumentMatchers.anyString())).willReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> registrationService.confirmToken("notFound"));
    }

    @Test
    public void confirmTokenNegative2Test() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.now());

        given(confirmationTokenService.getToken(ArgumentMatchers.anyString())).willReturn(Optional.of(confirmationToken));
        assertThrows(IllegalStateException.class, () -> registrationService.confirmToken("confirmed"));
    }

    @Test
    public void confirmTokenNegative3Test() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setExpiresAt(LocalDateTime.now());

        given(confirmationTokenService.getToken(ArgumentMatchers.anyString())).willReturn(Optional.of(confirmationToken));
        assertThrows(IllegalStateException.class, () -> registrationService.confirmToken("expired"));
    }

    @Test
    public void confirmTokenNegative4Test() {
        Account account = new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setAccount(account);
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        given(confirmationTokenService.getToken(ArgumentMatchers.anyString())).willReturn(Optional.of(confirmationToken));

        registrationService.confirmToken("token");

        verify(confirmationTokenService, times(1)).setConfirmedAt("token");
        verify(accountService, times(1)).enableUser(account.getEmail());
    }
}