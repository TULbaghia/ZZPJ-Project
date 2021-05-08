package pl.lodz.p.it.zzpj.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty {
    EASY(1), MIDEASY(2), MID(3), MIDHARD(4), HARD(5);

    private final int level;
}
