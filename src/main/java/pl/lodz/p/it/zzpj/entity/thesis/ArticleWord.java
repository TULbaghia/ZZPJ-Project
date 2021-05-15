package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = ArticleWord.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_article_id", unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article_id", "word_id"}, name = ArticleWord.IX_UQ_ID)
})
@Entity
@Data
public class ArticleWord {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "article_word";
    public static final String IX_UQ_ID = "ix_uq_articleword_id";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "article_id")
    Article article;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "word_id")
    Word word;

    @NonNull
    private Integer count;
}
