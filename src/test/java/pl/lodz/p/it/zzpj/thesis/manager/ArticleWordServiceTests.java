package pl.lodz.p.it.zzpj.thesis.manager;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.thesis.ArticleWord;
import pl.lodz.p.it.zzpj.entity.thesis.Word;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleWordService;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleRepository;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Sql(scripts = {"classpath:drop_all.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ArticleWordServiceTests {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleWordService articleWordService;

    @SneakyThrows
    private void translateWordsFromArticleWords(List<ArticleWord> articleWords) {
        Method translate = ArticleWordService.class
                .getDeclaredMethod("translateWordsFromArticleWords", List.class);
        translate.setAccessible(true);
        translate.invoke(articleWordService, articleWords);
    }

    @Test
    public void generateForIdTest() throws AppBaseException {
        Article article = new Article("10.1007/s12648-020-01105-4",
                "Test1",
                "art posture gramma count");

        articleRepository.saveAndFlush(article);
        articleWordService.generateForId(article.getId());

        List<Word> wordsWithTranslation = article.getArticleWordsList().stream()
                .map(ArticleWord::getWord)
                .collect(Collectors.toList());

        wordsWithTranslation.forEach(x -> {
            if (!x.isUseless()) {
                Assertions.assertNotEquals(x.getTranslation(), "");
            }
        });
    }

    @Test
    public void translateWordsFromArticleWordsTest() {
        Article article = new Article("10.1007/s12648-020-01105-4", "Test1", "Test1");

        List<ArticleWord> articleWordList =  List.of("art", "posture", "gramma", "count").stream()
                .map(x -> new Word(x, ""))
                .map(x -> new ArticleWord(article, x, 1))
                .collect(Collectors.toList());

        translateWordsFromArticleWords(articleWordList);

        articleWordList.forEach(x -> {
            if (!x.getWord().isUseless()) {
                Assertions.assertNotNull(x.getWord().getTranslation());
                Assertions.assertNotEquals(x.getWord().getTranslation(), "");
            }
        });
    }
}
