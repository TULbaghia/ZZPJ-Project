package pl.lodz.p.it.zzpj.controllers;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.zzpj.dtos.AccountDto;
import pl.lodz.p.it.zzpj.managers.AccountService;
import pl.lodz.p.it.zzpj.mappers.IAccountMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

//    @GetMapping
//    public List<AccountDto> getAllAccounts() {
//        IAccountMapper iAccountMapper = Mappers.getMapper(IAccountMapper.class);
//        return accountService.getAllAccounts().stream().map((x) -> iAccountMapper::toAccount).collect(Collectors.toList());
//    }
}
