package pl.lodz.p.it.zzpj.entities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.AbstractEntity;
import pl.lodz.p.it.zzpj.entities.account.AccountEntity;
import pl.lodz.p.it.zzpj.entities.enums.Translated;
import pl.lodz.p.it.zzpj.utils.validation.Score;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@Table(name = "card_deck_attempt")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CardDeckAttemptEntity.findAll", query = "SELECT a FROM CardDeckAttemptEntity a"),
        @NamedQuery(name = "CardDeckAttemptEntity.findById", query = "SELECT a FROM CardDeckAttemptEntity a WHERE a.id = :id"),
        @NamedQuery(name = "CardDeckAttemptEntity.findByCardDeck", query = "SELECT a FROM CardDeckAttemptEntity a WHERE a.cardDeck.id = :id"),
        @NamedQuery(name = "CardDeckAttemptEntity.findByScore", query = "SELECT a FROM CardDeckAttemptEntity a WHERE a.score = :score"),
        @NamedQuery(name = "CardDeckAttemptEntity.findByAccount", query = "SELECT a FROM CardDeckAttemptEntity a WHERE a.account.id = :id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class CardDeckAttemptEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id")
    @SequenceGenerator(name = "seq_account_id", allocationSize = 1)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Setter
    @JoinColumn(name = "card_deck_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private CardDeckEntity cardDeck;

    @Setter
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private AccountEntity account;

    @Setter
    @Basic(optional = false)
    @Column(name = "score", nullable = false)
    @Score
    private BigDecimal score;

    @Setter
    @Basic(optional = false)
    @Column(name = "translated", nullable = false)
    @Enumerated(EnumType.STRING)
    private Translated translated;
}
