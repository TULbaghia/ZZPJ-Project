package pl.lodz.p.it.zzpj.entities.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ArticleWordKey implements Serializable {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "word_id")
    private Long wordId;
}
