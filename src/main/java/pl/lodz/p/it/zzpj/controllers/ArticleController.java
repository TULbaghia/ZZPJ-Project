package pl.lodz.p.it.zzpj.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.dtos.ArticleListDto;
import pl.lodz.p.it.zzpj.dtos.FetchArticleDto;
import pl.lodz.p.it.zzpj.entities.model.Article;
import pl.lodz.p.it.zzpj.exceptions.ApiException;
import pl.lodz.p.it.zzpj.exceptions.NoRecordsException;
import pl.lodz.p.it.zzpj.managers.ArticleService;
import pl.lodz.p.it.zzpj.managers.TopicService;
import pl.lodz.p.it.zzpj.mappers.IArticleMapper;
import pl.lodz.p.it.zzpj.utils.ThesisFilter;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/article")
@AllArgsConstructor
public class ArticleController {

    private final RestTemplate restTemplate;
    private final ArticleService articleService;
    private final TopicService topicService;

    private final String TOPIC_GET = "http://api.springernature.com/meta/v2/json?q=subject:%s&s=%d&p=%d&api_key=30fb3f3f29b1ac21fdea732e62eddac1";
//    private final String DOI_GET = "http://api.springernature.com/meta/v2/json?q=doi:%s&s=%d&p=%d&api_key=30fb3f3f29b1ac21fdea732e62eddac1";

    @PostMapping(path = "byTopic")
    @ResponseBody
    public void fetchArticlesByTopic(@RequestBody FetchArticleDto fetchArticleDto) throws ApiException, NoRecordsException {
        Long topicId = Long.parseLong(fetchArticleDto.getId());
        String topic = topicService.getTopic(topicId).getName();

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.getForEntity(String.format(TOPIC_GET, topic, fetchArticleDto.getStart(),
                    fetchArticleDto.getCount()), String.class);
        } catch (RestClientException e) {
            throw new ApiException(e.getCause());
        }

        JsonElement articles = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
        ArticleListDto articleListDto = Mappers.getMapper(IArticleMapper.class).toArticleListDto(articles.getAsJsonObject(), topic);
        if (articleListDto.getRecords().isEmpty()) {
            throw new NoRecordsException("No records for given topic.");
        }

        articleService.storeArticle(articleListDto, topicId);
    }

    @GetMapping(path = "{articleId}")
    @ResponseBody
    public void getWordForArticle(@PathVariable Long articleId) {
        Article article = articleService.getArticle(articleId);

        String thesisAbstract = article.getThesisAbstract();
        HashMap<String, Integer> wordsCount = ThesisFilter.filterWord(thesisAbstract);


        int i = 5;
    }

//    @PostMapping(path = "byDoi")
//    @ResponseBody
//    public void fetchArticlesByDoi(@RequestBody FetchArticleDto fetchArticleDto) throws ApiException {
//        ResponseEntity<String> response = null;
//        try {
//            response = restTemplate.getForEntity(String.format(DOI_GET, fetchArticleDto.getId(), fetchArticleDto.getStart(),
//                    fetchArticleDto.getCount()), String.class);
//        } catch (RestClientException e) {
//            throw new ApiException(e.getCause());
//        }
//
//        JsonElement articles = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
//        ArticleListDto articleListDto = Mappers.getMapper(IArticleMapper.class).toArticleListDto(articles.getAsJsonObject(), topic);
//
//        articleService.storeArticle(articleListDto, topicId);
//    }
}
