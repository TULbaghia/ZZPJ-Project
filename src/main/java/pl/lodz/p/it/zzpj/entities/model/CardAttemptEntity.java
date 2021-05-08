package pl.lodz.p.it.zzpj.entities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.AbstractEntity;
import pl.lodz.p.it.zzpj.utils.validation.UserInput;
import pl.lodz.p.it.zzpj.utils.validation.Points;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@Table(name = "card_attempt")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CardAttemptEntity.findAll", query = "SELECT a FROM CardAttemptEntity a"),
        @NamedQuery(name = "CardAttemptEntity.findById", query = "SELECT a FROM CardAttemptEntity a WHERE a.id = :id"),
        @NamedQuery(name = "CardAttemptEntity.findByPoints", query = "SELECT a FROM CardAttemptEntity a WHERE a.points = :score"),
        @NamedQuery(name = "CardAttemptEntity.findByUserInput", query = "SELECT a FROM CardAttemptEntity a WHERE a.userInput = :input"),
        @NamedQuery(name = "CardAttemptEntity.findByCard", query = "SELECT a FROM CardAttemptEntity a WHERE a.cardEntity.id = :id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class CardAttemptEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id")
    @SequenceGenerator(name = "seq_account_id", allocationSize = 1)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Setter
    @JoinColumn(name = "card_deck_attempt_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private CardDeckAttemptEntity cardDeckAttempt;

    @Setter
    @JoinColumn(name = "card_deck_attempt_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private CardEntity cardEntity;

    @Setter
    @UserInput
    @Basic(optional = false)
    @Column(name = "from", nullable = false)
    private String userInput;

    @Setter
    @Points
    @Basic(optional = false)
    @Column(name = "from", nullable = false)
    private BigDecimal points;
}
