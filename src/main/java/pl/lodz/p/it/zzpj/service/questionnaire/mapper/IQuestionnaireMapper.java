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

    QuestionnaireDto toPlainDto(QuestionnaireDto questionnaireDto);

    @Mappings({
            @Mapping(target = "word", source = "word.word"),
            @Mapping(target = "userTranslation", source = "response"),
            @Mapping(target = "correctTranslation", source = "word.translation"),
    })
    QuestionnaireQuestionDto toDto(QuestionnaireQuestion questionnaireQuestion);

    @Mappings({
            @Mapping(target = "userTranslation", ignore = true),
            @Mapping(target = "correctTranslation", ignore = true),
    })
    QuestionnaireQuestionDto toDto(QuestionnaireQuestionDto questionnaireQuestion);

    default Set<QuestionnaireQuestionDto> map1(Set<QuestionnaireQuestion> questionnaireQuestions) {
        return questionnaireQuestions
                .stream()
                .map(IQuestionnaireMapper.INSTANCE::toDto)
                .collect(Collectors.toSet());
    }

    default Set<QuestionnaireQuestionDto> map2(Set<QuestionnaireQuestionDto> questionnaireQuestionsDto) {
        return questionnaireQuestionsDto
                .stream()
                .map(IQuestionnaireMapper.INSTANCE::toDto)
                .collect(Collectors.toSet());
    }

}
