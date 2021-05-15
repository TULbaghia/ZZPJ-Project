package pl.lodz.p.it.zzpj.controllers;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.dtos.AccountDto;
import pl.lodz.p.it.zzpj.entities.user.Account;
import pl.lodz.p.it.zzpj.managers.AccountService;
import pl.lodz.p.it.zzpj.mappers.IAccountMapper;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        IAccountMapper iAccountMapper = Mappers.getMapper(IAccountMapper.class);
        return accountService.getAllAccounts().stream().map(iAccountMapper::toAccountDto).collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public AccountDto getAccountByEmail(@PathVariable String email) {
        IAccountMapper iAccountMapper = Mappers.getMapper(IAccountMapper.class);
        return iAccountMapper.toAccountDto((Account) accountService.loadUserByUsername(email));
    }

    @PutMapping("/admin/{email}")
    public Response addAdminPermissions(@PathVariable String email) {
        accountService.addAdminPermissions(email);
        return Response.ok().build();
    }

//    @PutMapping("/{email}")
//    public ResponseStatus editAccount(@RequestBody AccountDto accountDto) {
//
//    }
}
