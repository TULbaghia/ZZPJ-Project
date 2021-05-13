package pl.lodz.p.it.zzpj.managers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.entities.model.Topic;
import pl.lodz.p.it.zzpj.repositories.TopicRepository;

import java.util.List;

@Service
@AllArgsConstructor
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
}
