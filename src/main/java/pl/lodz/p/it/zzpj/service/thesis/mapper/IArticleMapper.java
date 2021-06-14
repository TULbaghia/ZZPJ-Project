package pl.lodz.p.it.zzpj.service.thesis.mapper;

import com.google.gson.JsonObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.zzpj.entity.thesis.Article;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.exception.ApiException;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleDto;
import pl.lodz.p.it.zzpj.service.thesis.validator.pattern.ThesisRegularExpression;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper
public interface IArticleMapper {

    IArticleMapper INSTANCE = Mappers.getMapper(IArticleMapper.class);

    default List<String> toDoiList(JsonObject jsonObject) throws ApiException {
        if (jsonObject.get("result").getAsJsonArray().get(0).getAsJsonObject().get("recordsDisplayed").getAsInt() == 0) {
            throw new ApiException("Empty result list");
        }

        return StreamSupport.stream(jsonObject.get("records").getAsJsonArray().spliterator(), true)
                .map(x -> ((JsonObject) x).get("doi").getAsString())
                .filter(x -> x != null && x.matches(ThesisRegularExpression.DOI))
                .collect(Collectors.toList());
    }

    default ArticleDto toArticleDto(JsonObject jsonObject) throws ApiException {
        if (jsonObject.get("result").getAsJsonArray().get(0).getAsJsonObject().get("recordsDisplayed").getAsInt() == 0) {
            throw new ApiException("Empty result list");
        }

        var article = jsonObject.get("records").getAsJsonArray().get(0).getAsJsonObject();

        var subjectList = StreamSupport.stream(jsonObject.get("facets").getAsJsonArray().spliterator(), true)
                .map(x -> (JsonObject) x)
                .filter(x -> "subject".equals(x.get("name").getAsString()))
                .map(x -> x.get("values").getAsJsonArray())
                .flatMap(x -> StreamSupport.stream(x.spliterator(), false))
                .map(x -> ((JsonObject) x).get("value").getAsString())
                .collect(Collectors.toList());

        return ArticleDto.builder()
                .doi(article.get("doi").getAsString())
                .title(article.get("title").getAsString())
                .thesisAbstract(article.get("abstract").getAsString())
                .topicList(subjectList)
                .build();
    }

    @Mapping(target = "topicList", ignore = true)
    Article toArticle(ArticleDto articleDto);

    ArticleDto toArticleDto(Article article);

    @Mapping(target = "thesisAbstract", ignore = true)
    ArticleDto toArticleDto_NoDescription(Article article);

    default List<String> map(Set<Topic> topicSet) {
        return topicSet.stream()
                .map(Topic::getName)
                .collect(Collectors.toList());
    }
}
