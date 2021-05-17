package pl.lodz.p.it.zzpj.service.thesis.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.service.thesis.dto.WordDto;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleService;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleWordService;
import pl.lodz.p.it.zzpj.service.thesis.manager.TopicService;
import pl.lodz.p.it.zzpj.service.thesis.manager.WordService;
import pl.lodz.p.it.zzpj.service.thesis.mapper.IWordMapper;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/question")
@AllArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class QuestionController {
    private static final int EPOCHS_NUMBER = 0;
    private static final int RETURN_SIZE = 20;

    private final TopicService topicService;
    private final ArticleService articleService;
    private final ArticleWordService articleWordService;
    private final WordService wordService;

    @GetMapping(path = "{topicId}")
    @ResponseBody
    public Set<WordDto> getQuestions(@PathVariable Long topicId) {
        List<Set<Long>> topArticles = new ArrayList<>();
        topArticles.add(topicService.findArticleIdsConnectedWithTopics(Set.of(topicId)));

        Set<Long> usedTopics = new HashSet<>();
        usedTopics.add(topicId);

        for (int i = 0; i < EPOCHS_NUMBER; i++) {
            // Tematy, które są powiązane z wcześniej występującymi, ale zostały wykryte ostatniej epoce
            var topics = articleService.findTopicIdsFromArticlesIdsWithoutBannedTopic(
                    topArticles.stream().flatMap(Collection::stream).collect(Collectors.toSet()),
                    usedTopics
            );

            // Dodanie tematów do zbioru, tematów, które już wystąpiły
            usedTopics.addAll(topics);

            // Artykuły, które nie wystąpiły wcześniej w topArticles, a mają powiązanie z nowymi tematami
            // które wystąpiły w ostatniej epoce
            var uniqueArticlesConnected = topicService.findArticleIdsConnectedWithTopicsWithoutBannedArticles(
                    topics,
                    topArticles.stream().flatMap(Collection::stream).collect(Collectors.toSet())
            );

            // Dodanie artykułów do zbioru artykułów, które już wystąpiły
            topArticles.add(uniqueArticlesConnected);
        }

        // Wykonanie kwerend na bazie danych
        List<Set<Object[]>> articleWords = new ArrayList<>();
        topArticles.forEach(x -> {
            articleWords.add(articleWordService.findArticleWordsFromArticleIds(x));
        });

        // HashMap dodany w celu ułatwienia odrzucenia słów, które są poza rozpatrywanymi zbiorami "trafionych" słów
        HashMap<Long, Set<Long>> articleWithWordIdList = new HashMap<>();
        articleWords.stream()
                .flatMap(Collection::stream)
                .forEach(x -> {
                    Long awId = ((BigInteger) x[0]).longValue();
                    Long wordId = ((BigInteger) x[1]).longValue();
                    if (articleWithWordIdList.containsKey(wordId)) {
                        articleWithWordIdList.get(wordId).add(awId);
                    } else {
                        Set<Long> newHashSet = new HashSet<>();
                        newHashSet.add(awId);
                        articleWithWordIdList.put(wordId, newHashSet);
                    }
                });

        // Id słów, które są częścią abstraktów artykułów, powiązanych z tematem podanym przez użytkownika lub powiązanymi
        // Słowa, które są dozwolone do wykorzystania w procesie tworzenia testów dla użytkownika.
        Set<Long> wordIdsAllowed = new HashSet<>();
        articleWithWordIdList.entrySet()
                .parallelStream()
                .forEach(x -> {
                    if (articleWordService.getOtherArticleWordsForWord(x.getKey(), x.getValue()) == 0L) {
                        wordIdsAllowed.add(x.getKey());
                    }
                });

        List<Set<Long>> wordIdsWithEpochPriority = new ArrayList<>();

        articleWords.forEach(x -> {
            var words = x.stream()
                    .map(y -> ((BigInteger) y[1]).longValue())
                    .collect(Collectors.toSet());
            words.retainAll(wordIdsAllowed);

            wordIdsWithEpochPriority.add(words);
        });

        for (int i = 0; i < wordIdsWithEpochPriority.size() - 1; i++) {
            for (int j = i + 1; j < wordIdsWithEpochPriority.size(); j++) {
                wordIdsWithEpochPriority.get(i)
                        .removeAll(wordIdsWithEpochPriority.get(j));
            }
        }

        var allowedWords = wordIdsWithEpochPriority
                .stream()
                .map(x -> {
                    var list = new ArrayList<>(x);
                    Collections.shuffle(list);
                    return list;
                })
                .flatMap(Collection::stream)
                .limit(RETURN_SIZE)
                .map(wordService::getWord)
                .map(IWordMapper.INSTANCE::toWordDto)
                .collect(Collectors.toSet());


        return allowedWords;
    }
}
