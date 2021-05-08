package pl.lodz.p.it.zzpj.entities.account.role;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "admin_role")
@DiscriminatorValue("ADMIN")
@NamedQueries({
        @NamedQuery(name = "AdminRoleEntity.findAll", query = "SELECT m FROM AdminRoleEntity m"),
        @NamedQuery(name = "AdminRoleEntity.findById", query = "SELECT m FROM AdminRoleEntity m WHERE m.id = :id")})
@NoArgsConstructor
public class AdminRoleEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;
}
