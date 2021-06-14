package pl.lodz.p.it.zzpj.service.thesis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.zzpj.service.thesis.validator.Subject;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    @NotNull
    private Long id;

    @NotNull
    @Subject
    private String name;
}
