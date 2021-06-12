package pl.lodz.p.it.zzpj.service.questionnaire.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.exception.ApiException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class TranslationApi {
    private static final String API_KEY = "vhNLDq1dwIB7sqwqCBVdOOB1MFcJnv1YaSRlTiQBAzaX";
    private static final String BASE_URL = "https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/bbb164ce-0990-4a49-951f-3bc7ca4ae95c";

    private final RestTemplate restTemplate;

    @SneakyThrows
    public String translateWord(String word) {
        var requestBody = requestUrl(List.of(word));
        return requestBody
                .getAsJsonArray("translations")
                .get(0)
                .getAsJsonObject()
                .get("translation")
                .getAsString();
    }

    @SneakyThrows
    public List<String> translateWord(List<String> words) {
        var requestBody = requestUrl(words);

        return StreamSupport
                .stream(requestBody.get("translations").getAsJsonArray().spliterator(), false)
                .map(x -> x.getAsJsonObject().get("translation").getAsString())
                .collect(Collectors.toList());
    }

    private JsonObject requestUrl(List<String> words) throws ApiException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("apikey", API_KEY);

            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            jsonObject.add("text", gson.toJsonTree(words));
            jsonObject.get("text").getAsJsonArray();
            jsonObject.add("model_id", new JsonPrimitive("en-pl"));

            HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);

            var responseEntity = restTemplate.postForEntity(BASE_URL + "/v3/translate?version=2018-05-01", request, String.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new RestClientException("Response entity is not 2xx");
            }
            return JsonParser.parseString(Objects.requireNonNull(responseEntity.getBody())).getAsJsonObject();
        } catch (RestClientException e) {
            throw new ApiException(e.getCause());
        }
    }
}
