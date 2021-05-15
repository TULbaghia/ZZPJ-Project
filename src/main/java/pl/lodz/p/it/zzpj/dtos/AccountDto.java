package pl.lodz.p.it.zzpj.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entities.user.AccountRole;
import pl.lodz.p.it.zzpj.validators.Email;

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
