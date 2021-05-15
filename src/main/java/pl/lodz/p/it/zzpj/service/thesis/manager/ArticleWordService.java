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

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, rollbackFor = AppBaseException.class)
public class ArticleWordService {

    private final ArticleRepository articleRepository;

    private final ArticleWordRepository articleWordRepository;

    private final WordRepository wordRepository;

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    public void generateForId(Long id) {
        var article = articleRepository.getById(id);
        var thesis = article.getThesisAbstract();
        var words = ThesisFilter.filterWord(thesis);

        var articleWords = words.entrySet()
                .stream()
                .map( (entry) -> {
                    var word = wordRepository.findByWord(entry.getKey());
                    if(word == null) {
                        word = new Word(entry.getKey());
                    }
                    return new ArticleWord(article, word, entry.getValue());
                })
                .collect(Collectors.toList());

        articleWordRepository.saveAllAndFlush(articleWords);
    }

}
