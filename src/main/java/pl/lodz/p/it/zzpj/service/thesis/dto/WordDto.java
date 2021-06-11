package pl.lodz.p.it.zzpj.service.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private Long id;
    private String translation;
    private String word;
}
