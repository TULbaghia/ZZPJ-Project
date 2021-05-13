package pl.lodz.p.it.zzpj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entities.key.ArticleWordKey;
import pl.lodz.p.it.zzpj.entities.model.ArticleWord;

@Repository
@Transactional(readOnly = true)
public interface ArticleWordRepository extends JpaRepository<ArticleWord, ArticleWordKey> {
}
