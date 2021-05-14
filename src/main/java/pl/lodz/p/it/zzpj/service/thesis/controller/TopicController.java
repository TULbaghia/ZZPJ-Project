package pl.lodz.p.it.zzpj.service.thesis.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.service.thesis.dto.TopicDto;
import pl.lodz.p.it.zzpj.service.thesis.manager.TopicService;
import pl.lodz.p.it.zzpj.service.thesis.mapper.ITopicMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/topic")
@Transactional(propagation = Propagation.NEVER)
public class TopicController {

    private final TopicService topicService;

    @PostMapping(path = "/add/{name}")
    @ResponseBody
    public TopicDto addTopic(@PathVariable String name) {
        return ITopicMapper.INSTANCE.toTopicDto(topicService.addTopic(name));
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public TopicDto getTopic(@PathVariable Long id) {
        return ITopicMapper.INSTANCE.toTopicDto(topicService.getTopic(id));
    }

    @GetMapping
    @ResponseBody
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics()
                .stream()
                .map(ITopicMapper.INSTANCE::toTopicDto)
                .collect(Collectors.toList());
    }

    // TODO: updateTopic - dodawanie do topicu
//    @PutMapping(path = "{id}")
//    @ResponseBody
//    public TopicDto updateTopic() {
//        return new TopicDto();
//    }


    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public void deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
    }
}
