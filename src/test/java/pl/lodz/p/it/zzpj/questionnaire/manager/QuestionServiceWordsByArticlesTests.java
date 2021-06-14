package pl.lodz.p.it.zzpj.questionnaire.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.lodz.p.it.zzpj.service.questionnaire.manager.QuestionService;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
@Sql(scripts = {"classpath:drop_all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:init_question_service.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class QuestionServiceWordsByArticlesTests {

    @Autowired
    private QuestionService questionService;

    @Test
    @Order(0)
    public void shouldReturnArticleWordConnectedWithArticle1() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(1L));

        var allowedIds = questionService.getWordsIdsByArticles(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(1, allowedIds.size());
        Assertions.assertEquals(1, allowedIds.get(0).size());
        Assertions.assertEquals(1, allowedIds.get(0).stream().findFirst().orElseThrow());
    }

    @Test
    @Order(1)
    public void shouldReturnArticleWordsConnectedWithEveryArticleId() {
        var topArticles = new ArrayList<Set<Long>>();
        Set<Long> range = LongStream.range(1, 10).boxed().collect(Collectors.toSet());
        topArticles.add(range);

        var allowedIds = questionService.getWordsIdsByArticles(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(1, allowedIds.size());
        Assertions.assertEquals(9, allowedIds.get(0).size());
    }

    @Test
    @Order(2)
    public void shouldContainWordId123() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(1L));
        topArticles.add(Set.of(2L));
        topArticles.add(Set.of(3L));

        var allowedIds = questionService.getWordsIdsByArticles(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(3, allowedIds.size());
        Assertions.assertEquals(1, allowedIds.get(0).size());
        Assertions.assertEquals(1, allowedIds.get(1).size());
        Assertions.assertEquals(1, allowedIds.get(2).size());

        Assertions.assertEquals(1, allowedIds.get(0).stream().findFirst().orElseThrow());
        Assertions.assertEquals(2, allowedIds.get(1).stream().findFirst().orElseThrow());
        Assertions.assertEquals(3, allowedIds.get(2).stream().findFirst().orElseThrow());
    }

}
