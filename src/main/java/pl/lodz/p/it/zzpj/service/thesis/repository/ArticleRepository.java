package pl.lodz.p.it.zzpj.service.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.exception.AppBaseException;

import java.util.Set;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT DISTINCT t.id FROM article a JOIN topic_article ta on a.id = ta.article_id JOIN topic t on ta.topic_id = t.id WHERE a.id IN (:articleIds) AND t.id NOT IN (:bannedTopicIds)", nativeQuery = true)
    Set<Long> findTopicIdsFromArticlesIdsWithoutBannedTopic(@Param("articleIds") Set<Long> articleIds, @Param("bannedTopicIds") Set<Long> bannedTopicIds);
}
