package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.exception.NoRecordsException;
import pl.lodz.p.it.zzpj.exception.TopicException;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic addTopic(String name) throws TopicException {
        Topic topic = new Topic(name);
        try {
            return topicRepository.save(topic);
        } catch (DataIntegrityViolationException e) {
            throw TopicException.topicAlreadyExists(e);
        }
    }

    public Topic getTopic(Long id) throws NoRecordsException {
        return topicRepository.findById(id).orElseThrow(NoRecordsException::new);
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
