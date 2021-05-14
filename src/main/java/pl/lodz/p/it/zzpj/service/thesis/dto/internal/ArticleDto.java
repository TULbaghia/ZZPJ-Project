package pl.lodz.p.it.zzpj.service.thesis.dto.internal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String doi;
    private String thesisAbstract;
}
