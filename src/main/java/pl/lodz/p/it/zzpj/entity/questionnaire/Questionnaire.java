package pl.lodz.p.it.zzpj.entity.questionnaire;

import lombok.*;
import pl.lodz.p.it.zzpj.entity.user.Account;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = Questionnaire.TABLE_NAME, indexes = {
        @Index(columnList = "id", name = "ix_questionnaire_id", unique = true),
        @Index(columnList = "account_id", name = Questionnaire.IX_ACCOUNT),
})
@Entity
@Data
public class Questionnaire {

    @EqualsAndHashCode.Include
    public static final String TABLE_NAME = "questionnaire";
    public static final String IX_ACCOUNT = "ix_account";

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    @EqualsAndHashCode.Include
    private Long id;

    @NonNull
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private boolean isSolved = false;

    @ToString.Exclude
    @OneToMany(mappedBy = "questionnaire", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();
}
