package pl.lodz.p.it.zzpj.mappers;

import org.mapstruct.Mapper;
import pl.lodz.p.it.zzpj.entities.model.CardEntity;

@Mapper(componentModel = "spring")
public interface MapperExample {
    CardEntity toDto();
}

