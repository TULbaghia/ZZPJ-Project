package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entities.enums.AccessLevel;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminRoleDto extends RoleDto {
    public AdminRoleDto(Long id, AccessLevel accessLevel, boolean enabled) {
        super(id, accessLevel, enabled);
    }
}
