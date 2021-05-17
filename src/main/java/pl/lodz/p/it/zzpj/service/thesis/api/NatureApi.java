package pl.lodz.p.it.zzpj.service.thesis.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.exception.ApiException;
import pl.lodz.p.it.zzpj.exception.AppBaseException;
import pl.lodz.p.it.zzpj.service.thesis.dto.internal.ArticleDto;
import pl.lodz.p.it.zzpj.service.thesis.mapper.IArticleMapper;
import pl.lodz.p.it.zzpj.service.thesis.validator.Doi;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class NatureApi {
    private static final String API_KEY = "f2bfd2ed05590ef7ac4412d3fa700002";
    private static final String BASE_URL = "https://api.springernature.com/meta/v2/json?api_key=" + API_KEY;
    private static final String TOPIC_URL = BASE_URL + "&q=subject:%s&s=%d&p=%d";
    private static final String DOI_URL = BASE_URL + "&q=doi:%s&s=1&p=1";

    private final RestTemplate restTemplate;

    public ArticleDto getByDoi(@Doi String doi) throws AppBaseException {
        var requestBody = requestUrl(String.format(DOI_URL, doi));
        return IArticleMapper.INSTANCE.toArticleDto(requestBody);
    }

    public List<String> getByTopic(String topic, int start, int pagination) throws AppBaseException {
        var requestBody = requestUrl(String.format(TOPIC_URL, topic, start, pagination));
        return IArticleMapper.INSTANCE.toDoiList(requestBody);
    }

    private JsonObject requestUrl(String url) throws ApiException {
        try {
            var responseEntity = restTemplate.getForEntity(url, String.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RestClientException("Response entity is not 2xx");
            }
            return JsonParser.parseString(Objects.requireNonNull(responseEntity.getBody())).getAsJsonObject();
        } catch (RestClientException e) {
            throw new ApiException(e.getCause());
        }
    }
}
