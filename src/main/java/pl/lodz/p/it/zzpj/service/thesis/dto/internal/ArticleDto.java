package pl.lodz.p.it.zzpj.service.thesis.dto.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.service.thesis.validator.Doi;
import pl.lodz.p.it.zzpj.service.thesis.validator.Subject;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    @NotNull
    private Long id;

    @Doi
    private String doi;

    @Subject
    private String title;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String thesisAbstract;

    private List<String> topicList;
}
