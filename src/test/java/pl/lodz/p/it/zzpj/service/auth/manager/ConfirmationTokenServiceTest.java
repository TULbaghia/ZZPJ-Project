package pl.lodz.p.it.zzpj.service.auth.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lodz.p.it.zzpj.entity.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.service.auth.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @InjectMocks
    ConfirmationTokenService confirmationTokenService;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    public void saveConfirmationTokenTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken();

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        verify(confirmationTokenRepository, times(1)).save(confirmationToken);
    }

    @Test
    public void getTokenTest() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        given(confirmationTokenRepository.findByToken(ArgumentMatchers.anyString()))
                .willReturn(java.util.Optional.of(confirmationToken));

        assertEquals(confirmationToken, confirmationTokenService.getToken("token").get());

        verify(confirmationTokenRepository, times(1)).findByToken("token");
    }

    @Test
    public void setConfirmedAtTest() {

        confirmationTokenService.setConfirmedAt("token");

        verify(confirmationTokenRepository, times(1))
                .updateConfirmedAt(ArgumentMatchers.anyString(), ArgumentMatchers.any(LocalDateTime.class));
    }
}