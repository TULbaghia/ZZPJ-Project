package pl.lodz.p.it.zzpj.service.questionnaire.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.questionnaire.QuestionnaireQuestion;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireQuestionDto;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface IQuestionnaireMapper {

    IQuestionnaireMapper INSTANCE = Mappers.getMapper(IQuestionnaireMapper.class);

    QuestionnaireDto toDto(Questionnaire questionnaire);

    @Mappings({
            @Mapping(target = "word", source = "word.word"),
            @Mapping(target = "userTranslation", source = "response")
    })
    QuestionnaireQuestionDto toDto(QuestionnaireQuestion questionnaireQuestion);

    default Set<QuestionnaireQuestionDto> map(Set<QuestionnaireQuestion> questionnaireQuestions) {
        return questionnaireQuestions
                .stream()
                .map(IQuestionnaireMapper.INSTANCE::toDto)
                .collect(Collectors.toSet());
    }

}
