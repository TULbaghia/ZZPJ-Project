package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entities.enums.AccessLevel;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRoleDto extends RoleDto {
    public UserRoleDto(Long id, AccessLevel accessLevel, boolean enabled) {
        super(id, accessLevel, enabled);
    }
}
