package pl.lodz.p.it.zzpj.service.questionnaire.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.auth.repository.AccountRepository;
import pl.lodz.p.it.zzpj.service.questionnaire.dto.QuestionnaireDto;
import pl.lodz.p.it.zzpj.service.questionnaire.mapper.IQuestionnaireMapper;
import pl.lodz.p.it.zzpj.service.questionnaire.repository.QuestionnaireRepository;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final AccountRepository accountRepository;

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
