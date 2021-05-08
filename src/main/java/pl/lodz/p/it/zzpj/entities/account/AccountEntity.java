package pl.lodz.p.it.zzpj.entities.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.AbstractEntity;
import pl.lodz.p.it.zzpj.utils.validation.Password;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import static pl.lodz.p.it.zzpj.entities.account.AccountEntity.EMAIL_UQ;

@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(name = EMAIL_UQ, columnNames = {"email"})
}, indexes = {
        @Index(name = "ix_account_created_by", columnList = "created_by"),
        @Index(name = "ix_account_modified_by", columnList = "modified_by")
})
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AccountEntity.findAll", query = "SELECT a FROM AccountEntity a"),
        @NamedQuery(name = "AccountEntity.findById", query = "SELECT a FROM AccountEntity a WHERE a.id = :id"),
        @NamedQuery(name = "AccountEntity.findByEmail", query = "SELECT a FROM AccountEntity a WHERE a.email = :email"),
        @NamedQuery(name = "AccountEntity.findByEnabled", query = "SELECT a FROM AccountEntity a WHERE a.enabled = :enabled")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class AccountEntity extends AbstractEntity {

    public static final String EMAIL_UQ = "uk_account_login";

    public static final String CONTACT_NUMBER_UQ = "uk_account_contact_number";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id")
    @SequenceGenerator(name = "seq_account_id", allocationSize = 1)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Setter
    @NotNull
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @NotNull
    @Password
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Setter
    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public AccountEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Long getId() {
        return id;
    }
}
