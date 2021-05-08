package pl.lodz.p.it.zzpj.entities.account.role;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@DiscriminatorValue("USER")
@NamedQueries({
        @NamedQuery(name = "UserRoleEntity.findAll", query = "SELECT m FROM UserRoleEntity m"),
        @NamedQuery(name = "UserRoleEntity.findById", query = "SELECT m FROM UserRoleEntity m WHERE m.id = :id")})
@NoArgsConstructor
@ToString(callSuper = true)
public class UserRoleEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;
}
