package pl.lodz.p.it.zzpj.service.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchArticleDto {
    @NotNull
    private String id;

    @NotNull
    private Long start;

    @NotNull
    private Long count;
}
