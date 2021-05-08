package pl.lodz.p.it.zzpj.entities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.zzpj.entities.account.AccountEntity;
import pl.lodz.p.it.zzpj.utils.validation.CardDeckDescription;
import pl.lodz.p.it.zzpj.utils.validation.CardDeckName;
import pl.lodz.p.it.zzpj.utils.validation.Phrase;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "card_deck")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CardDeckEntity.findAll", query = "SELECT a FROM CardDeckEntity a"),
        @NamedQuery(name = "CardDeckEntity.findById", query = "SELECT a FROM CardDeckEntity a WHERE a.id = :id"),
        @NamedQuery(name = "CardDeckEntity.findByName", query = "SELECT a FROM CardDeckEntity a WHERE a.name = :name"),
        @NamedQuery(name = "CardDeckEntity.findByAccount", query = "SELECT a FROM CardDeckEntity a WHERE a.account.id = :id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class CardDeckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_id")
    @SequenceGenerator(name = "seq_account_id", allocationSize = 1)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    @Setter
    @JoinColumn(name = "account_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)
    private AccountEntity account;

    @Setter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "cardDeck")
    private Set<CardEntity> cardList = new HashSet<>();

    @Setter
    @Phrase
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @CardDeckName
    private String name;

    @Setter
    @Phrase
    @Basic(optional = false)
    @Column(name = "description", nullable = false)
    @CardDeckDescription
    private String description;
}
