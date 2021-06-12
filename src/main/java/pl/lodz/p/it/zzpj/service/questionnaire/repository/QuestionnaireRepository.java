package pl.lodz.p.it.zzpj.service.questionnaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.questionnaire.Questionnaire;
import pl.lodz.p.it.zzpj.entity.user.Account;
import pl.lodz.p.it.zzpj.exception.AppBaseException;

import java.util.Set;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query("SELECT q.id FROM Questionnaire q WHERE q.account = :account")
    Set<Long> findByAccount(@Param("account") Account account);
}
