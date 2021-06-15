package pl.lodz.p.it.zzpj.service.auth.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lodz.p.it.zzpj.entity.token.ConfirmationToken;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.service.auth.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void loadUserByUsername() {
        given(accountRepository.findByEmail(ArgumentMatchers.anyString())).willThrow(
                UsernameNotFoundException.class);
        given(accountRepository.findByEmail(ArgumentMatchers.eq("adam@adam.pl")))
                .willReturn(java.util.Optional
                        .of(new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER)));

        assertEquals("adam@adam.pl", accountService.loadUserByUsername("adam@adam.pl").getUsername());
        assertThrows(UsernameNotFoundException.class, () -> accountService.loadUserByUsername("OtherLogin"));
        verify(accountRepository, times(2)).findByEmail(ArgumentMatchers.anyString());
    }

    @Test
    public void getAllUsers() {
        given(accountRepository.findAll()).willReturn(prepareMockData());

        assertEquals("adam@adam.pl", accountService.getAllAccounts().get(0).getEmail());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    public void addAdminPermission() {
        Account account = new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER);
        given(accountRepository.findByEmail(ArgumentMatchers.eq("adam@adam.pl")))
                .willReturn(java.util.Optional
                        .of(account));
        accountService.addAdminPermissions("adam@adam.pl");
        verify(accountRepository, times(1)).findByEmail("adam@adam.pl");
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void singUpUserNegativeCase() {
        Account account = new Account("Adam", "Adamski", "testowy@testowy.pl", "password", AccountRole.USER);
        given(accountRepository.findByEmail(ArgumentMatchers.anyString()))
                .willReturn(java.util.Optional
                        .of(new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER)));
        assertThrows(IllegalStateException.class, () -> accountService.singUpUser(account));
    }

    @Test
    public void singUpUserPositiveCase() {
        Account account = new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER);
        given(accountRepository.findByEmail(ArgumentMatchers.eq("adam@adam.pl")))
                .willReturn(Optional.empty());
        accountService.singUpUser(account);
        verify(accountRepository, times(1)).findByEmail("adam@adam.pl");
        verify(accountRepository, times(1)).save(account);
    }

    private List<Account> prepareMockData() {
        List<Account> userList = new ArrayList<>();
        userList.add(new Account("Adam", "Adamski", "adam@adam.pl", "password", AccountRole.USER));
        return userList;
    }
}