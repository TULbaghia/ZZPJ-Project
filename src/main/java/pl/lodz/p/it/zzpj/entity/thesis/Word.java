package pl.lodz.p.it.zzpj.entity.thesis;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Word {
    @Id
    @SequenceGenerator(
            name = "word_sequence",
            sequenceName = "word_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "word_sequence"
    )

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;

    private String words;
}
