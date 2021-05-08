package pl.lodz.p.it.zzpj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.entities.enums.Difficulty;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long cardId;
    private Long cardDeckId;
    private String from;
    private String to;
    private Difficulty difficulty;
    private Long translatedById;
}
