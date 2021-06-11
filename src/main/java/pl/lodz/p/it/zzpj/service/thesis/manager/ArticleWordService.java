package pl.lodz.p.it.zzpj.service.thesis.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.zzpj.entity.thesis.ArticleWord;
import pl.lodz.p.it.zzpj.entity.thesis.Word;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.ArticleWordRepository;
import pl.lodz.p.it.zzpj.service.thesis.repository.WordRepository;
import pl.lodz.p.it.zzpj.service.thesis.utils.ThesisFilter;
import pl.lodz.p.it.zzpj.service.questionnaire.api.TranslationApi;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class ArticleWordService {

    private final ArticleRepository articleRepository;

    private final ArticleWordRepository articleWordRepository;

    private final WordRepository wordRepository;

    private final TranslationApi translationApi;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void generateForId(Long articleId) {
        var article = articleRepository.getById(articleId);
        var thesis = article.getThesisAbstract();
        var words = ThesisFilter.filterWord(thesis);

        var articleWords = words.entrySet()
                .stream()
                .map((entry) -> {
                    var word = wordRepository.findByWord(entry.getKey());
                    if (word == null) {
                        word = new Word(entry.getKey(), "");
                    }
                    return new ArticleWord(article, word, entry.getValue());
                })
                .collect(Collectors.toList());

        translateWordsFromArticleWords(articleWords);

        articleWordRepository.saveAllAndFlush(articleWords);
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    protected void translateWordsFromArticleWords(List<ArticleWord> articleWords) {
        List<Word> translationWords = articleWords.stream()
                .map(ArticleWord::getWord)
                .filter(x -> "".equals(x.getTranslation()) && x.getId() == null && !x.isUseless())
                .collect(Collectors.toList());

        List<String> translations = translationApi.translateWord(translationWords
                .stream()
                .map(Word::getWord)
                .collect(Collectors.toList())
        );

        for (int i = 0; i < translationWords.size(); i++) {
            var word = translationWords.get(i);
            word.setTranslation(translations.get(i));

            if(word.getWord().equalsIgnoreCase(word.getTranslation())
                    || word.getTranslation().length() <= 2) {
                word.setUseless(true);
            }
        }
    }

    public Set<Long> findMatchingArticleWord(Set<Long> articleIds) {
        return articleWordRepository.findMatchingArticleWord(articleIds);
    }
}
