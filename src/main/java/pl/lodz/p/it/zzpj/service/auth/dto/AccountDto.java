package pl.lodz.p.it.zzpj.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.service.auth.validator.Email;
import pl.lodz.p.it.zzpj.service.auth.validator.Firstname;
import pl.lodz.p.it.zzpj.service.auth.validator.Lastname;
import pl.lodz.p.it.zzpj.service.auth.validator.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Firstname
    private String firstName;
    @Lastname
    private String lastName;
    @Email
    private String email;
    @Role
    private AccountRole accountRole;
    private Boolean locked;
    private Boolean enabled;
}
