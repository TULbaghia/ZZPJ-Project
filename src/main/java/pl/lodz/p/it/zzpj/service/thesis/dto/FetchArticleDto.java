package pl.lodz.p.it.zzpj.service.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchArticleDto {
    private String id;
    private Long start;
    private Long count;
}
