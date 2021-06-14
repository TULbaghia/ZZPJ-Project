package pl.lodz.p.it.zzpj.service.thesis.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.service.thesis.validator.Subject;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    @Subject
    private String subject;

    private List<ArticleDto> records;
}
