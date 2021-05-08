package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDeckAttemptDto {
    private Long cardDeckAttemptId;
    private String translated;
    private BigDecimal score;
}
