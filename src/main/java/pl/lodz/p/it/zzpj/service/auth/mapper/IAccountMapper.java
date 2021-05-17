package pl.lodz.p.it.zzpj.service.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.service.auth.dto.AccountDto;

@Mapper
public interface IAccountMapper {

//    @Mapping(source = "accountRole", target = "accountRole")
//    Account toAccount(AccountDto accountDto);

    @Mapping(source = "accountRole", target = "accountRole")
    AccountDto toAccountDto(Account account);
}
