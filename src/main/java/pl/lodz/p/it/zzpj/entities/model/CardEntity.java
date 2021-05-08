package pl.lodz.p.it.zzpj.entities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.AbstractEntity;
import pl.lodz.p.it.zzpj.entities.account.AccountEntity;
import pl.lodz.p.it.zzpj.entities.enums.Difficulty;
import pl.lodz.p.it.zzpj.utils.validation.Phrase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "card")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CardEntity.findAll", query = "SELECT a FROM CardEntity a"),
        @NamedQuery(name = "CardEntity.findById", query = "SELECT a FROM CardEntity a WHERE a.id = :id"),
        @NamedQuery(name = "CardEntity.findByFrom", query = "SELECT a FROM CardEntity a WHERE a.from = :from"),
        @NamedQuery(name = "CardEntity.findByTo", query = "SELECT a FROM CardEntity a WHERE a.to = :to"),
        @NamedQuery(name = "CardEntity.findByDifficulty", query = "SELECT a FROM CardEntity a WHERE a.difficulty = :difficulty"),
        @NamedQuery(name = "CardEntity.findByTranslator", query = "SELECT a FROM CardEntity a WHERE a.translatedBy = :translator"),
        @NamedQuery(name = "CardEntity.getCardsInDeck", query = "SELECT a FROM CardEntity a WHERE a.cardDeck.id = :id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class CardEntity extends AbstractEntity {

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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "cardEntity")
    private Set<CardAttemptEntity> cardAttemptList = new HashSet<>();

    @Setter
    @Phrase
    @Basic(optional = false)
    @Column(name = "from", nullable = false)
    private String from;

    @Setter
    @Phrase
    @Basic(optional = false)
    @Column(name = "to", nullable = false)
    private String to;

    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "difficulty", nullable = false)
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Setter
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH)
    private AccountEntity translatedBy = null;
}