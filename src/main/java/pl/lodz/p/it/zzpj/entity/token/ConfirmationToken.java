package pl.lodz.p.it.zzpj.entity.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.zzpj.entity.user.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = ConfirmationToken.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = ConfirmationToken.IX_ACCOUNT_ID, unique = true),
        @Index(columnList = "token", name = ConfirmationToken.IX_UQ_TOKEN, unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token"}, name = ConfirmationToken.IX_UQ_TOKEN)
})
@Entity
public class ConfirmationToken {

    public static final String TABLE_NAME = "confirmation_token";
    public static final String IX_ACCOUNT_ID = "ix_account_id";
    public static final String IX_UQ_TOKEN = "ix_uq_token";

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Account account) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.account = account;
    }
}
