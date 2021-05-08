package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDeckDto {
    private Long accountId;
    private String name;
    private String description;
}
