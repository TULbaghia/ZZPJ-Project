package pl.lodz.p.it.zzpj.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.zzpj.dtos.TopicDto;
import pl.lodz.p.it.zzpj.entities.model.Topic;

@Mapper
public interface ITopicMapper {

    ITopicMapper INSTANCE = Mappers.getMapper(ITopicMapper.class);

    Topic toTopic(TopicDto topicDto);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    TopicDto toTopicDto(Topic topic);
}
