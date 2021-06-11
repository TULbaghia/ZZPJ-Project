package pl.lodz.p.it.zzpj.service.questionnaire.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionnaireService;

import java.util.Set;

@RestController
@RequestMapping(path = "api/questionnaire")
@AllArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    @GetMapping(path = "/q/{questionnaireId}")
    @ResponseBody
    public QuestionnaireDto getQuestionnaireById(@PathVariable Long questionnaireId) {
        return questionnaireService.getQuestionnaire(questionnaireId);
    }

    @GetMapping(path = "/q")
    @ResponseBody
    public Set<Long> getQuestionnairesForUser() {
        return questionnaireService.getQuestionnaireForUser();
    }
}
