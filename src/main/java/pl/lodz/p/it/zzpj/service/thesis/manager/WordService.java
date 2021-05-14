package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.service.thesis.repository.WordRepository;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class WordService {

    private final WordRepository wordRepository;

    public String addTopic(String name) {
//        Topic topic = new Topic(name);
//        return wordRepository.save(topic);
        return "";
    }
}
