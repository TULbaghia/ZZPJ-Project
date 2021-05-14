package pl.lodz.p.it.zzpj.service.thesis.mapper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleDto;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleListDto;
import pl.lodz.p.it.zzpj.entity.thesis.Article;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface IArticleMapper {

    default ArticleListDto toArticleListDto(JsonObject jsonObject, String subject) {
        List<ArticleDto> list = new ArrayList<>();

        for (JsonElement e : jsonObject.get("records").getAsJsonArray()) {
            list.add(toArticleDto((JsonObject) e));
        }

        return ArticleListDto.builder()
                .subject(subject)
                .records(list)
                .build();
    }

    default ArticleDto toArticleDto(JsonObject jsonObject) {
        return ArticleDto.builder()
                .doi(jsonObject.get("doi").getAsString())
                .thesisAbstract(jsonObject.get("abstract").getAsString())
                .build();
    }

    @Mapping(target = "topicList", ignore = true)
    Article toArticle(ArticleDto articleDto);

    ArticleDto toArticleDto(Article article);
}
