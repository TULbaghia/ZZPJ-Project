package pl.lodz.p.it.zzpj.service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.service.auth.validator.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private AccountRole accountRole;
    private Boolean locked;
    private Boolean enabled;
}
