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

    @Query(value = "SELECT DISTINCT aw.word_id FROM article_word aw " +
            "JOIN word w on aw.word_id = w.id " +
            "WHERE aw.article_id IN (:articleIds) AND w.is_useless = false", nativeQuery = true)
    Set<Long> findMatchingArticleWord(@Param("articleIds") Set<Long> articleIds);

    /**
     * Pobieramy słowa, które znajdują się w artykułach, które przerzuciliśmy
     * I ilość wystąpień w innych artykułach tych słów musi być równa null
     *
     * @param articleIds lista artykułów
     * @return lista unikalnych słów dla podanej listy artykułów
     */
    @Query(value = "SELECT DISTINCT aw.word_id " +
            "FROM article_word aw " +
            "LEFT JOIN (" +
            "   SELECT DISTINCT aw2.word_id, COUNT(*) as bannedCount " +
            "   FROM article_word aw2 " +
            "   WHERE NOT EXISTS ( " +
            "       SELECT DISTINCT aw3.id" +
            "       FROM article_word aw3 " +
            "       WHERE aw3.article_id IN (:articleIds) AND aw2.word_id = aw3.word_id AND aw2.id = aw3.id )" +
            "   GROUP BY aw2.word_id " +
            ") aw2 ON aw2.word_id = aw.word_id " +
            "WHERE aw.article_id IN (:articleIds) AND aw2.bannedCount IS NULL", nativeQuery = true)
    Set<Long> findArticleWordsFromArticleIds(@Param("articleIds") Set<Long> articleIds);

}
