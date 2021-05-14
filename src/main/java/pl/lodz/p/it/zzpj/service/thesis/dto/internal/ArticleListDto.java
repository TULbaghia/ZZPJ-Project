package pl.lodz.p.it.zzpj.service.thesis.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDto {
    private String subject;
    private List<ArticleDto> records;
}
