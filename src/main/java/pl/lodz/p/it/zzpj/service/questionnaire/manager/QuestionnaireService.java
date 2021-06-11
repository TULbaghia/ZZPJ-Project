package pl.lodz.p.it.zzpj.service.questionnaire.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.questionnaire.QuestionnaireQuestion;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.auth.repository.AccountRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.mapper.IQuestionnaireMapper;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireQuestionRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireRepository;
import pl.lodz.p.it.zzpj.service.thesis.manager.WordService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class QuestionnaireService {
    private static final int EPOCHS_NUMBER = 0;
    private static final int RETURN_SIZE = 5;

    private final WordService wordService;
    private final QuestionService questionService;

    private final QuestionnaireQuestionRepository questionRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final AccountRepository accountRepository;

    public QuestionnaireDto getQuestions(Long topicId) {
        var topArticles = questionService.getArticlesConnectedWithTopic(topicId, EPOCHS_NUMBER);

        var wordIdsWithEpochPriority = questionService.getWordsIdsByArticles(topArticles);

        String emailPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();
        Questionnaire questionnaire = new Questionnaire(account);

        List<QuestionnaireQuestion> initMe =
                wordIdsWithEpochPriority
                        .stream()
                        .map(x -> {
                            var list = new ArrayList<>(x);
                            Collections.shuffle(list);
                            return list;
                        })
                        .flatMap(Collection::stream)
                        .limit(RETURN_SIZE)
                        .map(wordService::getWord)
                        .map(word -> {
                            Long id = word.getId();
                            if (id > 0) {
                                return new QuestionnaireQuestion(questionnaire, word);
                            }
                            return null;
                        })
                        .collect(Collectors.toList());

        questionRepository.saveAllAndFlush(initMe);

        questionnaire.setQuestionnaireQuestions(new HashSet<>(initMe));

        questionnaireRepository.saveAndFlush(questionnaire);

        return IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
    }

    public Set<Long> getQuestionnaireForUser() {
        String emailPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();
        return questionnaireRepository.findByAccount(account);
    }

    public QuestionnaireDto getQuestionnaire(Long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireId);

        return IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
    }
}
