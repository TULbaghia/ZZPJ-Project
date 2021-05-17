package pl.lodz.p.it.zzpj.service.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.exception.AppBaseException;

import java.util.Set;

@Repository
@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic t WHERE t.name = ?1")
    Topic findByName(String name);

    @Query(value = "SELECT DISTINCT a.id FROM topic t JOIN topic_article ta ON ta.topic_id = t.id JOIN article a ON ta.article_id = a.id WHERE t.id IN (:topicIds)", nativeQuery = true)
    Set<Long> findArticleIdsConnectedWithTopics(@Param("topicIds") Set<Long> topicIds);

    @Query(value = "SELECT DISTINCT a.id FROM topic t JOIN topic_article ta ON ta.topic_id = t.id JOIN article a ON ta.article_id = a.id WHERE t.id IN (:topicIds) AND a.id NOT IN (:bannedArticlesIds)", nativeQuery = true)
    Set<Long> findArticleIdsConnectedWithTopicsWithoutBannedArticles(@Param("topicIds") Set<Long> topicIds, @Param("bannedArticlesIds") Set<Long> bannedArticlesIds);

}
