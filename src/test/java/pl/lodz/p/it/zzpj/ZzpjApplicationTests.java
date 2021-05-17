package pl.lodz.p.it.zzpj;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.service.thesis.repository.TopicRepository;

@SpringBootTest
class ZzpjApplicationTests {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    void exampleTopicTest() {
        Topic topic = new Topic("TestowyTemat");
        topicRepository.save(topic);

        Assertions.assertNotNull(topic.getId());
        Assertions.assertEquals(topic.getId(), 1);
        Assertions.assertEquals(topic.getName(), "TestowyTemat");
    }
}
