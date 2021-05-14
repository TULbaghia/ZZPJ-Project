package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;
import pl.lodz.p.it.zzpj.entity.key.ArticleWordKey;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "article_word")
@Entity
public class ArticleWord {

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    @EmbeddedId
    private ArticleWordKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    Article article;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("wordId")
    @JoinColumn(name = "word_id")
    Word word;

    private int count;
}
