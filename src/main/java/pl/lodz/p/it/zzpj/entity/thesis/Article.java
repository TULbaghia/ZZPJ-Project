package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;
import pl.lodz.p.it.zzpj.service.thesis.validator.Doi;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Article.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_article_id", unique = true),
        @Index(columnList = "doi", name = Article.IX_UQ_DOI, unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doi"}, name = Article.IX_UQ_DOI)
})
@Entity
@Data
public class Article {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "article";
    public static final String IX_UQ_DOI = "ix_uq_article_doi";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @Doi
    @NonNull
    @Column(nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private String doi;

    @NonNull
    @Column(length = Short.MAX_VALUE, nullable = false, updatable = false)
    private String title;

    @NonNull
    @Column(length = Short.MAX_VALUE, nullable = false, updatable = false)
    private String thesisAbstract;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "topic_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topicList = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleWord> articleWordsList = new HashSet<>();
}
