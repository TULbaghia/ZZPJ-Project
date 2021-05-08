package pl.lodz.p.it.zzpj.entities.account.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.AbstractEntity;
import pl.lodz.p.it.zzpj.entities.account.AccountEntity;
import pl.lodz.p.it.zzpj.entities.enums.AccessLevel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import static pl.lodz.p.it.zzpj.entities.account.role.RoleEntity.ROLE_ACCESS_LEVEL_ACCOUNT_ID_UQ;

@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(name = ROLE_ACCESS_LEVEL_ACCOUNT_ID_UQ, columnNames = {"access_level", "account_id"})
}, indexes = {
        @Index(name = "ix_role_account_id", columnList = "account_id")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "access_level")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "RoleEntity.findAll", query = "SELECT r FROM RoleEntity r"),
        @NamedQuery(name = "RoleEntity.findById", query = "SELECT r FROM RoleEntity r WHERE r.id = :id"),
        @NamedQuery(name = "RoleEntity.findByEnabled", query = "SELECT r FROM RoleEntity r WHERE r.enabled = :enabled")})
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class RoleEntity extends AbstractEntity {

    public static final String ROLE_ACCESS_LEVEL_ACCOUNT_ID_UQ = "uk_role_access_level_account_id";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role_id")
    @SequenceGenerator(name = "seq_role_id", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Getter
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "access_level", updatable = false, nullable = false)
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled = true;

    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    private AccountEntity user;

    @Override
    public Long getId() {
        return id;
    }
}
