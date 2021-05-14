package pl.lodz.p.it.zzpj.service.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.key.ArticleWordKey;
import pl.lodz.p.it.zzpj.entity.thesis.ArticleWord;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
public interface ArticleWordRepository extends JpaRepository<ArticleWord, ArticleWordKey> {
}
