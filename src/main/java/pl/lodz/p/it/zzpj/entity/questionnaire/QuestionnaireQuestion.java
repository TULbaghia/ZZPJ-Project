package pl.lodz.p.it.zzpj.entity.questionnaire;

import lombok.*;
import pl.lodz.p.it.zzpj.entity.thesis.Word;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = QuestionnaireQuestion.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_questionnairequestion_id", unique = true),
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"questionnaire_id", "word_id"}, name = QuestionnaireQuestion.IX_UQ_QUESTIONNAIRE_WORD)
})
@Entity
@Data
public class QuestionnaireQuestion {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "questionnairequestion";
    public static final String IX_UQ_QUESTIONNAIRE_WORD = "ix_uq_questionnaire_word";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "questionnaire_id", nullable = false, updatable = false)
    private Questionnaire questionnaire;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "word_id", nullable = false, updatable = false)
    private Word word;

    private String response;

    private boolean isCorrect = false;
}
