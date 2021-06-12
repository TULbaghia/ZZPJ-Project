package pl.lodz.p.it.zzpj.entity.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.service.auth.validator.Email;
import pl.lodz.p.it.zzpj.service.auth.validator.Firstname;
import pl.lodz.p.it.zzpj.service.auth.validator.Lastname;
import pl.lodz.p.it.zzpj.service.auth.validator.Password;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = Account.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = Account.IX_ACCOUNT_ID, unique = true),
        @Index(columnList = "email", name = Account.IX_UQ_EMAIL, unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}, name = Account.IX_UQ_EMAIL)
})
@Entity
public class Account implements UserDetails {

    public static final String TABLE_NAME = "account";
    public static final String IX_ACCOUNT_ID = "ix_account_id";
    public static final String IX_UQ_EMAIL = "ix_uq_email";

    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    @Firstname
    private String firstName;
    @Lastname
    private String lastName;
    @Email
    private String email;
    @Password
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    public Account(String firstName, String lastName, String email, String password, AccountRole accountRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(accountRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "account", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Questionnaire> questionnaires = new HashSet<>();
}
