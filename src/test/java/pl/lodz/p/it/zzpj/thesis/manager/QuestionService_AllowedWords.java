package pl.lodz.p.it.zzpj.thesis.manager;

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
public class QuestionService_AllowedWords {

    @Autowired
    private QuestionService questionService;

    @Test
    @Order(0)
    public void shouldContainWordId1() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(1L));
        var allowedIds = questionService.getAllowedWordIds(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(1, allowedIds.stream().findFirst().orElseThrow());
    }

    @Test
    @Order(1)
    public void shouldContainNoWordId() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(2L));
        var allowedIds = questionService.getAllowedWordIds(topArticles);
        Assertions.assertTrue(allowedIds.isEmpty());
    }

    @Test
    @Order(2)
    public void shouldContain7TopArticles() {
        var topArticles = new ArrayList<Set<Long>>();
        Set<Long> range = LongStream.range(1, 10).boxed().collect(Collectors.toSet());
        topArticles.add(range);
        var allowedIds = questionService.getAllowedWordIds(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(9, allowedIds.size());
    }

    @Test
    @Order(3)
    public void shouldContainOnlyId4() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(2L, 3L, 4L));
        var allowedIds = questionService.getAllowedWordIds(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(4, allowedIds.stream().findFirst().orElseThrow());
    }

    @Test
    @Order(4)
    public void shouldContainAllTopArticles() {
        var topArticles = new ArrayList<Set<Long>>();
        topArticles.add(Set.of(5L, 6L, 7L));
        var allowedIds = questionService.getAllowedWordIds(topArticles);
        Assertions.assertFalse(allowedIds.isEmpty());
        Assertions.assertEquals(3, allowedIds.size());
        Assertions.assertTrue(allowedIds.contains(5L));
        Assertions.assertTrue(allowedIds.contains(6L));
        Assertions.assertTrue(allowedIds.contains(7L));
    }
}
