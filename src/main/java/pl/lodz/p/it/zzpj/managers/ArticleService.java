package pl.lodz.p.it.zzpj.managers;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.dtos.ArticleListDto;
import pl.lodz.p.it.zzpj.entities.model.Article;
import pl.lodz.p.it.zzpj.entities.model.Topic;
import pl.lodz.p.it.zzpj.mappers.IArticleMapper;
import pl.lodz.p.it.zzpj.repositories.*;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleWordRepository articleWordRepository;
    private final TopicRepository topicRepository;
    private final WordRepository wordRepository;

    @Transactional
    public void storeArticle(ArticleListDto articleListDto, Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();

        IArticleMapper iArticleMapper = Mappers.getMapper(IArticleMapper.class);
        Set<Article> articleList = articleListDto.getRecords().stream().map(iArticleMapper::toArticle).collect(Collectors.toSet());

        articleList.forEach(x -> x.getTopicList().add(topic));
        topic.setArticleList(articleList);

        topicRepository.save(topic);
    }

    public Article getArticle(Long id) {
        return articleRepository.getById(id);
    }
}
