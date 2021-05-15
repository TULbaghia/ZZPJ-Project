package pl.lodz.p.it.zzpj.entities.model;

import lombok.*;
import pl.lodz.p.it.zzpj.entities.key.ArticleWordKey;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
