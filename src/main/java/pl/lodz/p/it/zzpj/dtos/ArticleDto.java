package pl.lodz.p.it.zzpj.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private String doi;
    private String thesisAbstract;
}
