package pl.lodz.p.it.zzpj.service.thesis.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleDto;
import pl.lodz.p.it.zzpj.service.thesis.manager.ArticleService;
import pl.lodz.p.it.zzpj.service.thesis.mapper.IArticleMapper;
import pl.lodz.p.it.zzpj.service.thesis.validator.Doi;
import pl.lodz.p.it.zzpj.service.thesis.validator.Subject;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/article")
@AllArgsConstructor
@Transactional(propagation = Propagation.NEVER)
@Log
public class ArticleController {

    private final ArticleService articleService;

    @PutMapping(path = "add/byDoi")
    @ResponseBody
    public void insertByDoi(@NotNull @QueryParam("doi") @Doi String doi) throws AppBaseException {
        articleService.createFromDoi(doi);
    }

    @PutMapping(path = "add/{topic}/{start}/{pagination}")
    @ResponseBody
    public void insertArticleByTopic(@NotNull @PathVariable @Subject String topic, @PathVariable int start, @PathVariable int pagination) throws AppBaseException {
        var articleDtoList = articleService.createFromTopic(topic, start, pagination);
        articleDtoList.parallelStream().forEach(x -> {
            try {
                articleService.createFromDoi(x);
            } catch (DataIntegrityViolationException | AppBaseException e) {
                log.warning(e.getMessage());
            }
        });
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    public ArticleDto getArticle(@NotNull @PathVariable Long id) {
        return IArticleMapper.INSTANCE.toArticleDto(articleService.getArticle(id));
    }

    @GetMapping
    @ResponseBody
    public List<ArticleDto> getAllArticle() {
        return articleService.getAllArticle()
                .stream()
                .map(IArticleMapper.INSTANCE::toArticleDto_NoDescription)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "{id}")
    @ResponseBody
    public void deleteArticle(@NotNull @PathVariable Long id) {
        articleService.delete(id);
    }

}
