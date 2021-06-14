package pl.lodz.p.it.zzpj.service.questionnaire.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionnaireDto {
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer score;

    private boolean isSolved;

    Set<QuestionnaireQuestionDto> questionnaireQuestions;
}
