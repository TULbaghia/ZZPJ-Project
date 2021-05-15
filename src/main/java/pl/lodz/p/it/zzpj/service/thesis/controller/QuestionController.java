package pl.lodz.p.it.zzpj.service.thesis.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.thesis.ArticleWord;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleWordService;
import pl.lodz.p.it.zzpj.service.thesis.manager.TopicService;
import pl.lodz.p.it.zzpj.service.thesis.manager.WordService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/question")
@AllArgsConstructor
public class QuestionController {
    private static final int EPOCHS_NUMBER = 2;

    private final TopicService topicService;

    private final ArticleWordService articleWordService;

    @GetMapping(path = "{topicId}")
    @ResponseBody
    public String getQuestions(@PathVariable Long topicId) {
        Topic topic = topicService.getTopic(topicId);
        Set<Article> articleList = topic.getArticleList();

        List<Set<Article>> topArticles = new ArrayList<>();
        topArticles.add(articleList);
        Set<Topic> usedTopics = new HashSet<>();
        usedTopics.add(topic);

        for (int i = 0; i < EPOCHS_NUMBER; i++) {
            // Tematy, które są powiązane z wcześniej występującymi, ale zostały wykryte ostatniej epoce
            var topics = topArticles.stream()
                    .flatMap(Collection::stream)
                    .map(Article::getTopicList)
                    .flatMap(Collection::stream)
                    .filter(x -> !usedTopics.contains(x))
                    .collect(Collectors.toSet());

            // Dodanie tematów do zbioru, tematów, które już wystąpiły
            usedTopics.addAll(topics);

            // Wszystkie artykuły sprawdzone dotychczas, w celu zapewnienia unikalności, sprowadzone do Set
            var flatTopArticles = topArticles.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            // Artykuły, które nie wystąpiły wcześniej w topArticles, a mają powiązanie z nowymi tematami
            // które wystąpiły w ostatniej epoce
            var uniqueArticlesConnected = topics.stream()
                    .map(Topic::getArticleList)
                    .flatMap(Collection::stream)
                    .filter(x -> !flatTopArticles.contains(x))
                    .collect(Collectors.toSet());

            // Dodanie artykułów do zbioru artykułów, które już wystąpiły
            topArticles.add(uniqueArticlesConnected);
        }

        // ID artykułów z wszystkich epok wg. ważności
        List<Set<Long>> identificators = new ArrayList<>();

        // Lista zawierająca sety z ArticleWord, pochodzące z wszystkich epok wg. ważności
        List<Set<ArticleWord>> articleWords = new ArrayList<>();
        topArticles.parallelStream().forEach(x -> {
            identificators.add(x.parallelStream()
                    .map(Article::getId)
                    .collect(Collectors.toSet()));
        });

        // Wykonanie kwerend na bazie danych
        identificators.parallelStream().forEach(x -> {
            articleWords.add(articleWordService.getArticleWordsForArticle(x));
        });

        // HashMap dodany w celu ułatwienia odrzucenia słów, które są poza rozpatrywanymi zbiorami "trafionych" słów
        HashMap<Long, Set<Long>> articleWithWordIdList = new HashMap<>();
        articleWords.stream()
                .flatMap(Collection::stream)
                .forEach(x -> {
                    Long wordId = x.getWord().getId();
                    if (articleWithWordIdList.containsKey(wordId)) {
                        articleWithWordIdList.get(wordId).add(x.getId());
                    } else {
                        Set<Long> newHashSet = new HashSet<>();
                        newHashSet.add(x.getId());
                        articleWithWordIdList.put(x.getWord().getId(), newHashSet);
                    }
                });

        // Id słów, które są częścią abstraktów artykułów, powiązanych z tematem podanym przez użytkownika lub powiązanymi
        // Słowa, które są dozwolone do wykorzystania w procesie tworzenia testów dla użytkownika.
        HashMap<Long, Boolean> wordIdsAllowed = new HashMap<>();
        articleWithWordIdList.entrySet().parallelStream()
                .forEach(x -> {
                    Long isPresent = articleWordService
                            .getOtherArticleWordsForWord(x.getKey(), new ArrayList<>(x.getValue()));
                    wordIdsAllowed.put(x.getKey(), isPresent == 0);
                });

        return "";
    }
}
