package pl.lodz.p.it.zzpj.service.thesis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.zzpj.entity.thesis.Word;
import pl.lodz.p.it.zzpj.service.thesis.dto.WordDto;

@Mapper
public interface IWordMapper {

    IWordMapper INSTANCE = Mappers.getMapper(IWordMapper.class);

    WordDto toWordDto(Word word);
}
