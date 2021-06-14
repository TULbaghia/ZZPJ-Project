package pl.lodz.p.it.zzpj.service.questionnaire.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionnaireQuestionDto {
    private Long id;
    private String word;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String userTranslation;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String correctTranslation;
}
