package pl.lodz.p.it.zzpj.thesis.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleService;
import pl.lodz.p.it.zzpj.service.thesis.manager.TopicService;

import java.util.List;

@SpringBootTest
public class ArticleServiceTests {
    @Autowired
    private TopicService topicService;

    @Autowired
    private ArticleService articleService;

    @Test
    public void createFromDoiTest() throws AppBaseException {
        String testDoi = "10.1007/s10998-020-00359-6";
        int articlesSizeBefore = articleService.getAllArticle().size();
        int topicsSizeBefore = topicService.getAllTopics().size();

        articleService.createFromDoi(testDoi);

        int topicSizeAfter = topicService.getAllTopics().size();
        int articlesSizeAfter = articleService.getAllArticle().size();

        Assertions.assertEquals(articlesSizeAfter, articlesSizeBefore + 1);
        Assertions.assertTrue(topicSizeAfter > topicsSizeBefore);
    }

    @Test
    public void getArticleTest() throws AppBaseException {
        String testDoi = "10.1007/s12648-020-01805-4";
        articleService.createFromDoi(testDoi);

        List<Article> articleList = articleService.getAllArticle();

        articleList.forEach(x -> {
            Assertions.assertEquals(x.getTitle(),
                    articleService.getArticle(x.getId()).getTitle());
            Assertions.assertEquals(x.getTopicList().size(),
                    articleService.getArticle(x.getId()).getTopicList().size());
        });
    }

    @Test
    public void deleteArticleTest() throws AppBaseException {
        String testDoi = "10.1007/s00205-021-01653-4";
        articleService.createFromDoi(testDoi);
        int articlesSizeBefore = articleService.getAllArticle().size();

        Article article = articleService.getAllArticle()
                .stream()
                .filter(x -> x.getDoi().equals(testDoi))
                .findFirst()
                .orElseThrow();

        articleService.delete(article.getId());

        int articlesSizeAfterDelete = articleService.getAllArticle().size();
        Assertions.assertEquals(articlesSizeBefore - 1, articlesSizeAfterDelete);
    }
}
