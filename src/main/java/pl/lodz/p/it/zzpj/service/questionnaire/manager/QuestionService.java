package pl.lodz.p.it.zzpj.service.questionnaire.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.exception.NoRecordsException;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleService;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleWordService;
import pl.lodz.p.it.zzpj.service.thesis.manager.TopicService;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleWordRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class QuestionService {

    private final ArticleWordRepository articleWordRepository;

    private final TopicService topicService;
    private final ArticleService articleService;
    private final ArticleWordService articleWordService;

    public List<Set<Long>> getArticlesConnectedWithTopic(final Long topicId, final int epoch) throws NoRecordsException {
        var topic = topicService.getTopic(topicId);
        //Przechowuje listę epok zawierających zbiór artykułów pobranych w danej epoce
        List<Set<Long>> topArticles = new ArrayList<>();
        // Pobieranie artykułów bezpośrednio powiązanych z przekazanym tematem
        topArticles.add(topicService.findArticlesIdsByTopics(Set.of(topic.getId())));

        // Przechowuje tematy wykorzystane w poprzednich epokach
        Set<Long> usedTopics = new HashSet<>();
        usedTopics.add(topicId);

        for (int i = 0; i < epoch; i++) {
            var flattedTopArticles = topArticles.stream().flatMap(Collection::stream).collect(Collectors.toSet());

            // Pobiera tematy powiązane z tematami artykułów pobranych w poprzedniej epoce,
            // z których to tematów nie pobrano jeszcze artykułów
            var topics = articleService.findNotBannedTopicIdsFromArticleIds(flattedTopArticles, usedTopics);

            // Dodaje tematy pobrane w bieżącej epoce, do listy użytych tematów
            usedTopics.addAll(topics);

            // Pobiera artykuły powiązane z tematami pobranymi w bieżącej epoce,
            // nie występujące wcześniej na liście pobranych artykułów
            var uniqueArticlesConnected = topicService.findFilteredArticleIdsByTopics(topics, flattedTopArticles);

            // Dodaje artykuły do zbioru artykułów dla kolejnej epoki
            topArticles.add(uniqueArticlesConnected);
        }

        return topArticles;
    }

    public Set<Long> getAllowedWordIds(final List<Set<Long>> topArticles) {
        var flattedTopArticles = topArticles
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return articleWordRepository.findArticleWordsFromArticleIds(flattedTopArticles);
    }

    public List<Set<Long>> getWordsIdsByArticles(List<Set<Long>> topArticles) {
        // Wykonanie kwerend na bazie danych
        List<Set<Long>> wordsConnectedWithArticlesInEachEpoch = new ArrayList<>();
        topArticles.forEach(x -> {
            wordsConnectedWithArticlesInEachEpoch.add(articleWordService.findMatchingArticleWord(x));
        });

        Set<Long> awQuery = getAllowedWordIds(topArticles);

        List<Set<Long>> allowedWordsInEpochs = new ArrayList<>();

        wordsConnectedWithArticlesInEachEpoch.forEach(x -> {
            var words = new HashSet<>(x);
            words.retainAll(awQuery);
            allowedWordsInEpochs.add(words);
        });

        for (int i = 0; i < allowedWordsInEpochs.size() - 1; i++) {
            for (int j = i + 1; j < allowedWordsInEpochs.size(); j++) {
                allowedWordsInEpochs.get(i).removeAll(allowedWordsInEpochs.get(j));
            }
        }

        return allowedWordsInEpochs;
    }
}
