package pl.lodz.p.it.zzpj.questionnaire.manager;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.TestSecurityContext;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.questionnaire.QuestionnaireQuestion;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.entity.user.AccountRole;
import pl.lodz.p.it.zzpj.exception.*;
import pl.lodz.p.it.zzpj.service.auth.repository.AccountRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionnaireService;
import pl.lodz.p.it.zzpj.service.questionnaire.mapper.IQuestionnaireMapper;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireQuestionRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.WordRepository;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Sql(scripts = {"classpath:drop_all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:init_question_service.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class QuestionnaireServiceTests {
    @Autowired
    private QuestionnaireQuestionRepository questionRepository;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @SneakyThrows
    private List<QuestionnaireQuestion> getQuestionnaireQuestions(List<Set<Long>> wordIdsWithEpochPriority, Questionnaire questionnaire) {
        Method translate = QuestionnaireService.class
                .getDeclaredMethod("getQuestionnaireQuestions", List.class, Questionnaire.class);
        translate.setAccessible(true);
        return (List<QuestionnaireQuestion>) translate.invoke(questionnaireService, wordIdsWithEpochPriority, questionnaire);
    }


    @Test
    @Transactional
    public void getQuestionnaireQuestionsTest() {
        List<Set<Long>> wordIdsWithEpochPriority = new ArrayList<>();
        wordIdsWithEpochPriority.add(Set.of(1L, 5L, 6L));
        wordIdsWithEpochPriority.add(Set.of(2L, 3L));
        wordIdsWithEpochPriority.add(Set.of(4L));

        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        List<QuestionnaireQuestion> result = getQuestionnaireQuestions(wordIdsWithEpochPriority, questionnaire);
        result.forEach(x -> {
            Assertions.assertEquals(questionnaire.getId(), x.getQuestionnaire().getId());
            Assertions.assertEquals(account.getId(), x.getQuestionnaire().getAccount().getId());
            Assertions.assertNotNull(x.getWord());
        });

    }

    @Test
    @Transactional
    public void solveQuestionnaireAlreadySolved() {
        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaire.setSolved(true);
        questionnaireRepository.saveAndFlush(questionnaire);

        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        var exception = Assertions.assertThrows(AccessDeniedException.class,
                () -> questionnaireService.solveQuestionnaire(IQuestionnaireMapper.INSTANCE.toDto(questionnaire)));
        Assertions.assertEquals(exception.getMessage(), "exception.already_solved");
    }

    @Test
    @Transactional
    public void solveQuestionnaireNotPermitted() {
        Account account = new Account("Testadminz",
                "Testadmin",
                "testadmin1@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Account account1 = new Account("Testadminz",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account1);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        var exception = Assertions.assertThrows(AccessDeniedException.class,
                () -> questionnaireService.solveQuestionnaire(IQuestionnaireMapper.INSTANCE.toDto(questionnaire)));
        Assertions.assertEquals(exception.getMessage(), "exception.access.denied");
    }

    @Test
    @Transactional
    public void solveQuestionnaireNoSuchQuestion() throws NoRecordsException {
        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireQuestion qq = new QuestionnaireQuestion(questionnaire, wordRepository.getById(1L));
        questionnaire.getQuestionnaireQuestions().add(qq);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireDto qDto = IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());
        qDto.getQuestionnaireQuestions().stream().findFirst().orElseThrow(NoRecordsException::new).setId(-999L);

        var exception = Assertions.assertThrows(QuestionnaireException.class,
                () -> questionnaireService.solveQuestionnaire(qDto));
        Assertions.assertEquals(exception.getMessage(), "exception.questionnaire.no_such_question");
    }

    @Test
    @Transactional
    public void solveQuestionnaireWithoutQuestion() throws NoRecordsException {
        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireQuestion qq = new QuestionnaireQuestion(questionnaire, wordRepository.getById(1L));
        questionnaire.getQuestionnaireQuestions().add(qq);
        questionRepository.saveAndFlush(qq);
        
        QuestionnaireQuestion qq1 = new QuestionnaireQuestion(questionnaire, wordRepository.getById(2L));
        questionnaire.getQuestionnaireQuestions().add(qq1);
        questionRepository.saveAndFlush(qq1);

        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireDto qDto = IQuestionnaireMapper.INSTANCE.toDto(questionnaire);
        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());
        qDto.getQuestionnaireQuestions().removeIf(x -> x.getId().equals(qq1.getId()));

        var exception = Assertions.assertThrows(QuestionnaireException.class,
                () -> questionnaireService.solveQuestionnaire(qDto));
        Assertions.assertEquals(exception.getMessage(), "exception.questionnaire.no_such_question");
    }

    @Test
    public void banQuestionRelatedWordIdNotValidTest() {
        Assertions.assertThrows(NoRecordsException.class,
                () -> questionnaireService.banQuestionRelatedWord(-999L));
    }

    @Test
    @Transactional
    public void banQuestionRelatedWordAlreadyBannedTest() {
        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaire.setSolved(true);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireQuestion qq = new QuestionnaireQuestion(questionnaire, wordRepository.getById(1L));
        qq.getWord().setUseless(true);
        questionRepository.saveAndFlush(qq);
        var exception = Assertions.assertThrows(WordException.class,
                () -> questionnaireService.banQuestionRelatedWord(qq.getId()));
        Assertions.assertEquals(exception.getMessage(), "exception.word_already_useless");
    }

    @Test
    @Transactional
    public void banQuestionRelatedWordSuccessTest() {
        Account account = new Account("Testadmin",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        QuestionnaireQuestion qq = new QuestionnaireQuestion(questionnaire, wordRepository.getById(1L));
        questionRepository.saveAndFlush(qq);
        Assertions.assertDoesNotThrow(() -> questionnaireService.banQuestionRelatedWord(qq.getId()));
    }

    @Test
    @Transactional
    public void getQuestionnaireUserNotPermitted() {
        Account account = new Account("Testadminz",
                "Testadmin",
                "testadmin1@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Account account1 = new Account("Testadminz",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.USER);

        accountRepository.saveAndFlush(account1);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);

        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        var exception = Assertions.assertThrows(AccessDeniedException.class,
                () -> questionnaireService.getQuestionnaire(questionnaire.getId()));
        Assertions.assertEquals(exception.getMessage(), "exception.access.denied");
    }

    @Test
    @Transactional
    public void getQuestionnaireAdminPermitted() {
        Account account = new Account("Testadminz",
                "Testadmin",
                "testadmin1@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Account account1 = new Account("Testadminz",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account1);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);
        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        Assertions.assertDoesNotThrow(() -> questionnaireService.getQuestionnaire(questionnaire.getId()));
    }

    @Test
    @Transactional
    public void getQuestionnaireUserOwnQuestionnaire() {
        Account account = new Account("Testadminz",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);
        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        Assertions.assertDoesNotThrow(() -> questionnaireService.getQuestionnaire(questionnaire.getId()));
    }

    @Test
    @Transactional
    public void getQuestionsTest() throws AppBaseException {
        Account account = new Account("Testadminz",
                "Testadmin",
                "testadmin@op.pl",
                "zaq1@WSX",
                AccountRole.ADMIN);

        accountRepository.saveAndFlush(account);

        Questionnaire questionnaire = new Questionnaire(account);
        questionnaireRepository.saveAndFlush(questionnaire);
        SecurityContextHolder.getContext().setAuthentication(new TestSecurityContext());

        QuestionnaireDto qDto = questionnaireService.getQuestions(1L);
        Assertions.assertTrue(qDto.getQuestionnaireQuestions().size() > 0);

        Questionnaire getQuestionnaire = questionnaireRepository.getById(qDto.getId());
        Assertions.assertEquals(account.getEmail(), getQuestionnaire.getAccount().getEmail());
    }
}
