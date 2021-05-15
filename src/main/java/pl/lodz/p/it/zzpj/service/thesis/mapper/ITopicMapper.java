package pl.lodz.p.it.zzpj.service.thesis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.zzpj.entity.thesis.Topic;
import pl.lodz.p.it.zzpj.service.thesis.dto.TopicDto;

@Mapper
public interface ITopicMapper {

    ITopicMapper INSTANCE = Mappers.getMapper(ITopicMapper.class);

    @Mapping(target = "articleList", ignore = true)
    Topic toTopic(TopicDto topicDto);


    TopicDto toTopicDto(Topic topic);
}
