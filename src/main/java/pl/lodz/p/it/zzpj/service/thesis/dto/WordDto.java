package pl.lodz.p.it.zzpj.service.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    @NotNull
    private Long id;

    private String translation;

    private String word;
}
