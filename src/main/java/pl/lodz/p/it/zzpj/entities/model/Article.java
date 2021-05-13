package pl.lodz.p.it.zzpj.entities.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doi"}, name = Article.DOI_UNIQUE)
})
public class Article {

    public final static String DOI_UNIQUE = "uq_article_doi";

    @Id
    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String doi;

    @Column(length = Short.MAX_VALUE)
    private String thesisAbstract;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "topic_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topicList = new HashSet<>();
}
