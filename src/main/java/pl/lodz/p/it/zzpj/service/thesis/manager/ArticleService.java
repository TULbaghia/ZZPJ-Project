package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleListDto;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.service.thesis.mapper.IArticleMapper;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class ArticleService {

    private final TopicRepository topicRepository;

    private final ArticleRepository articleRepository;

    public void create() {

    }

    public Article getArticle(Long id) {
        return articleRepository.getById(id);
    }

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public void update() {}

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }



    public void storeArticle(ArticleListDto articleListDto, Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();

        IArticleMapper iArticleMapper = Mappers.getMapper(IArticleMapper.class);
        Set<Article> articleList = articleListDto.getRecords().stream().map(iArticleMapper::toArticle).collect(Collectors.toSet());

        articleList.forEach(x -> x.getTopicList().add(topic));
        topic.setArticleList(articleList);

        topicRepository.save(topic);
    }

}
