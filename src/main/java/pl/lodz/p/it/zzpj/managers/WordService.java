package pl.lodz.p.it.zzpj.managers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.zzpj.entities.model.Topic;
import pl.lodz.p.it.zzpj.repositories.WordRepository;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    public String addTopic(String name) {
//        Topic topic = new Topic(name);
//        return wordRepository.save(topic);
        return "";
    }
}
