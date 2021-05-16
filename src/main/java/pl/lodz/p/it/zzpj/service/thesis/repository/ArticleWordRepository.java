package pl.lodz.p.it.zzpj.service.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.ArticleWord;
import pl.lodz.p.it.zzpj.exception.AppBaseException;

import java.util.Set;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public interface ArticleWordRepository extends JpaRepository<ArticleWord, Long> {

    @Query("SELECT aw FROM ArticleWord aw WHERE (aw.article.id) IN (?1)")
    Set<ArticleWord> getArticleWordsForArticle(Set<Long> ids);

    @Query("SELECT COUNT (aw) FROM ArticleWord aw WHERE aw.word.id = ?1 AND aw.id NOT IN (?2)")
    Long getOtherArticleWordsForWord(Long wordId, Set<Long> articleWordIds);

    @Query(value = "SELECT DISTINCT aw.id, aw.word_id FROM article_word aw WHERE aw.article_id IN (:articleIds)", nativeQuery = true)
    Set<Object[]> findArticleWordsFromArticleIds(@Param("articleIds") Set<Long> articleIds);

}
