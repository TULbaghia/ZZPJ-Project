package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;
import pl.lodz.p.it.zzpj.entity.questionnaire.QuestionnaireQuestion;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @NonNull
    private String translation;

    private boolean isUseless = false;

    @ToString.Exclude
    @OneToMany(mappedBy = "word", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<QuestionnaireQuestion> questionnaireQuestionSet = new HashSet<>();
}
