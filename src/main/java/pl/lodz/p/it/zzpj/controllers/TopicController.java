package pl.lodz.p.it.zzpj.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.dtos.TopicDto;
import pl.lodz.p.it.zzpj.managers.TopicService;
import pl.lodz.p.it.zzpj.mappers.ITopicMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/topic")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping(path = "{name}")
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
        return topicService.getAllTopics().stream().map(ITopicMapper.INSTANCE::toTopicDto).collect(Collectors.toList());
    }

    // TODO: updateTopic - dodawanie do topicu
//    @PutMapping(path = "{id}")
//    @ResponseBody
//    public TopicDto updateTopic() {
//        return new TopicDto();
//    }


    @DeleteMapping(path = "{id}")
    @ResponseBody
    public boolean deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return true;
    }
}
