package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic addTopic(String name) {
        Topic topic = new Topic(name);
        return topicRepository.save(topic);
    }

    public Topic getTopic(Long id) {
        return topicRepository.getById(id);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    public Set<Long> findArticlesIdsByTopics(Set<Long> topicId) {
        return topicRepository.findArticleIdsByTopics(topicId);
    }

    public Set<Long> findFilteredArticleIdsByTopics(Set<Long> topicIds, Set<Long> bannedArticlesIds) {
        return topicRepository.findFilteredArticleIdsByTopics(topicIds, bannedArticlesIds);
    }
}
