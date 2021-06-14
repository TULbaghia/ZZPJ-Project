package pl.lodz.p.it.zzpj.service.questionnaire.manager;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.questionnaire.QuestionnaireQuestion;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.exception.*;
import pl.lodz.p.it.zzpj.service.auth.repository.AccountRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionService;
import pl.lodz.p.it.zzpj.service.questionnaire.mapper.IQuestionnaireMapper;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireQuestionRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireRepository;
import pl.lodz.p.it.zzpj.service.thesis.manager.WordService;

import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Log
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

    public QuestionnaireDto getQuestions(Long topicId) throws AppBaseException {
        String emailPrincipal = getEmailPrincipal();
        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();

        if(account.getQuestionnaires().stream().anyMatch(x -> !x.isSolved())) {
            throw QuestionnaireException.lastQuestionnaireNotSolved();
        }

        var topArticles = questionService.getArticlesConnectedWithTopic(topicId, EPOCHS_NUMBER);

        var wordIdsWithEpochPriority = questionService.getWordsIdsByArticles(topArticles);


        Questionnaire questionnaire = new Questionnaire(account);
        List<QuestionnaireQuestion> initMe = getQuestionnaireQuestions(wordIdsWithEpochPriority, questionnaire);
        questionnaire.setQuestionnaireQuestions(new HashSet<>(initMe));
        questionnaireRepository.saveAndFlush(questionnaire);

        var questionnaireDto = IQuestionnaireMapper.INSTANCE.toDto(questionnaire);

        return IQuestionnaireMapper.INSTANCE.toPlainDto(questionnaireDto);
    }

    public String getEmailPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    protected List<QuestionnaireQuestion> getQuestionnaireQuestions(List<Set<Long>> wordIdsWithEpochPriority, Questionnaire questionnaire) {
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
                .map(word -> new QuestionnaireQuestion(questionnaire, word))
                .collect(Collectors.toList());

        questionRepository.saveAllAndFlush(initMe);
        return initMe;
    }

    public Set<Long> getQuestionnaireForUser() {
        String emailPrincipal = getEmailPrincipal();

        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();
        return questionnaireRepository.findByAccount(account);
    }

    public QuestionnaireDto getQuestionnaire(Long questionnaireId) throws AppBaseException {
        String emailPrincipal = getEmailPrincipal();
        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();
        var questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(NoRecordsException::new);

        if(account.getAccountRole().equals(AccountRole.USER)
                && !questionnaire.getAccount().equals(account)) {
            throw new AccessDeniedException("exception.access.denied");
        }

        QuestionnaireDto qDto = IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
        if (qDto.isSolved()) {
            qDto.setScore((int) questionnaire.getQuestionnaireQuestions()
                    .stream()
                    .map(QuestionnaireQuestion::isCorrect)
                    .filter(x -> x)
                    .count());
            return qDto;
        }

        return IQuestionnaireMapper.INSTANCE.toPlainDto(qDto);
    }

    public QuestionnaireDto solveQuestionnaire(QuestionnaireDto questionnaireDto) throws AppBaseException {
        String emailPrincipal = getEmailPrincipal();

        Account account = accountRepository.findByEmail(emailPrincipal).orElseThrow();
        Questionnaire questionnaire = questionnaireRepository.getById(questionnaireDto.getId());

        if (!questionnaire.getAccount().equals(account)) {
            log.info("Exception access denied");
            throw new AccessDeniedException("exception.access.denied");
        }

        if (questionnaire.isSolved()) {
            log.info("Already solved");
            throw new AccessDeniedException("exception.already_solved");
        }

        for (final QuestionnaireQuestion x : questionnaire.getQuestionnaireQuestions()) {
            var first = questionnaireDto.getQuestionnaireQuestions()
                    .stream()
                    .filter(y -> x.getId().equals(y.getId())).findFirst();

            if (first.isEmpty()) {
                log.info("No such question");
                throw new QuestionnaireException("exception.questionnaire.no_such_question");
            }

            x.setResponse(first.get().getWord());

            x.setCorrect(x.getWord().getTranslation().equalsIgnoreCase(x.getResponse()));
        }

        questionnaire.setSolved(true);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireDto questionnaireDto1 = IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
        questionnaireDto1.setScore((int) questionnaire.getQuestionnaireQuestions()
                .stream()
                .map(QuestionnaireQuestion::isCorrect)
                .filter(x -> x)
                .count()
        );

        return questionnaireDto1;
    }

    public void banQuestionRelatedWord(Long questionId) throws AppBaseException {
        var question = questionRepository.findById(questionId).orElseThrow(NoRecordsException::new);

        if (question.getWord().isUseless()) {
            throw WordException.alreadyBanned();
        }

        question.getWord().setUseless(true);

        questionRepository.saveAndFlush(question);
    }
}
