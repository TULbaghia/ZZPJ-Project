package pl.lodz.p.it.zzpj.thesis.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionService;

@SpringBootTest
@Sql(scripts = {"classpath:drop_all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:init_question_service.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class QuestionService_EpochTests {

    @Autowired
    private QuestionService questionService;

    @Test
    @Order(0)
    public void shouldReturnDirectlyConnectedTopics() {
        long startTopic = 1;

        var topArticles = questionService.getArticlesConnectedWithTopic(startTopic, 0);

        Assertions.assertEquals(1, topArticles.size());
        Assertions.assertEquals(2, topArticles.get(0).size());
        Assertions.assertTrue(topArticles.get(0).contains(1L));
        Assertions.assertTrue(topArticles.get(0).contains(2L));
    }

    @Test
    @Order(1)
    public void shouldReturnDirectlyConnectedTopic() {
        long startTopic = 6;

        var topArticles = questionService.getArticlesConnectedWithTopic(startTopic, 0);

        Assertions.assertEquals(1, topArticles.size());
        Assertions.assertEquals(1, topArticles.get(0).size());
        Assertions.assertTrue(topArticles.get(0).contains(8L));
    }

    @Test
    @Order(2)
    public void shouldAlsoReturnTopicsFromEpoch1() {
        long startTopic = 1;

        var topArticles = questionService.getArticlesConnectedWithTopic(startTopic, 1);

        Assertions.assertEquals(2, topArticles.size());
        Assertions.assertEquals(2, topArticles.get(0).size());
        Assertions.assertEquals(2, topArticles.get(1).size());
        Assertions.assertTrue(topArticles.get(0).contains(1L));
        Assertions.assertTrue(topArticles.get(0).contains(2L));
        Assertions.assertTrue(topArticles.get(1).contains(8L));
        Assertions.assertTrue(topArticles.get(1).contains(9L));
    }

}
