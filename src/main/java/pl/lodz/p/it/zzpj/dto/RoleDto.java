package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entities.enums.AccessLevel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class RoleDto {
    private Long id;
    private AccessLevel accessLevel;
    private boolean enabled;
}
