package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Word.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_article_id", unique = true),
        @Index(columnList = "word", name = Word.IX_UQ_WORD, unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"word"}, name = Word.IX_UQ_WORD)
})
@Entity
@Data
public class Word {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "word";
    public static final String IX_UQ_WORD = "ix_uq_word_word";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(nullable = false, updatable = false)
    private String word;
}
