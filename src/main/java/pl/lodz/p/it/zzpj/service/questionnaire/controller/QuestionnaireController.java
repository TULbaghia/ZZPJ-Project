package pl.lodz.p.it.zzpj.service.questionnaire.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.exception.NoRecordsException;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionnaireService;

import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping(path = "api/questionnaire")
@AllArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @PostMapping(path = "{topicId}")
    @ResponseBody
    public QuestionnaireDto createQuestionnaireForTopic(@NotNull @PathVariable Long topicId) throws AppBaseException {
        return questionnaireService.getQuestions(topicId);
    }

    @GetMapping(path = "/q/{questionnaireId}")
    @ResponseBody
    public QuestionnaireDto getQuestionnaireById(@NotNull @PathVariable Long questionnaireId) throws AppBaseException {
        return questionnaireService.getQuestionnaire(questionnaireId);
    }

    @GetMapping(path = "/q")
    @ResponseBody
    public Set<Long> getQuestionnairesForUser() {
        return questionnaireService.getQuestionnaireForUser();
    }

    @PutMapping(path = "/q")
    @ResponseBody
    public QuestionnaireDto resolveQuestionnaire(@NotNull @RequestBody QuestionnaireDto questionnaireDto) throws AppBaseException {
        return questionnaireService.solveQuestionnaire(questionnaireDto);
    }

    @PatchMapping(path = "/ban/{questionId}")
    public void banQuestionRelatedWord(@NotNull @PathVariable Long questionId) throws AppBaseException {
        questionnaireService.banQuestionRelatedWord(questionId);
    }
}
