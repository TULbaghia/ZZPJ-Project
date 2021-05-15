package pl.lodz.p.it.zzpj.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.zzpj.dtos.AccountDto;
import pl.lodz.p.it.zzpj.entities.user.Account;

@Mapper
public interface IAccountMapper {

    @Mapping(source = "accountRole", target = "accountRole")
    Account toAccount(AccountDto accountDto);
}
