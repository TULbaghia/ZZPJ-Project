package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.api.NatureApi;
import pl.lodz.p.it.zzpj.service.thesis.mapper.IArticleMapper;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;
import pl.lodz.p.it.zzpj.service.thesis.validator.Doi;
import pl.lodz.p.it.zzpj.service.thesis.validator.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class ArticleService {

    private final TopicRepository topicRepository;

    private final ArticleRepository articleRepository;

    private final NatureApi natureApi;

    private final ArticleWordService articleWordService;

    public void createFromDoi(@Doi String doi) throws AppBaseException {
        var articleDto = natureApi.getByDoi(doi);
        var article = IArticleMapper.INSTANCE.toArticle(articleDto);

        List<Topic> topics = new ArrayList<>();

        articleDto.getTopicList().forEach(x -> {
            var topic = topicRepository.findByName(x);
            topics.add(topic == null ? new Topic(x) : topic);
        });

        topics.forEach(x -> {
            x.getArticleList().add(article);
            article.getTopicList().add(x);
        });

        articleRepository.saveAndFlush(article);
        articleWordService.generateForId(article.getId());
    }

    public List<String> createFromTopic(@Subject String topic, int start, int pagination) throws AppBaseException {
        return natureApi.getByTopic(topic, start, pagination);
    }

    public Article getArticle(Long id) {
        return articleRepository.getById(id);
    }

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public Set<Long> findNotBannedTopicIdsFromArticleIds(Set<Long> articleIds, Set<Long> bannedTopicIds) {
        return articleRepository.findNotBannedTopicIdsFromArticleIds(articleIds, bannedTopicIds);
    }
}
