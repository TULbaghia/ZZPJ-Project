package pl.lodz.p.it.zzpj.service.thesis.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.zzpj.exception.ApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log
@Service
@AllArgsConstructor
public class TranslationApi {
    private static final String API_KEY = "vhNLDq1dwIB7sqwqCBVdOOB1MFcJnv1YaSRlTiQBAzaX";
    private static final String BASE_URL = "https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/bbb164ce-0990-4a49-951f-3bc7ca4ae95c";
    private static final String URL_API_VERSION = BASE_URL + "/v3/translate?version=2018-05-01";

    private final RestTemplate restTemplate;

    public List<String> translateWord(List<String> words) throws ApiException {
        if(words.isEmpty()) {
            return new ArrayList<>();
        }

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

            var responseEntity = restTemplate.postForEntity(URL_API_VERSION, request, String.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                log.info("Response entity is not 2xx");
                throw new RestClientException("Response entity is not 2xx");
            }
            return JsonParser.parseString(Objects.requireNonNull(responseEntity.getBody())).getAsJsonObject();
        } catch (RestClientException e) {
            log.info("Exception occurred: " + e.getClass());
            throw new ApiException(e.getCause());
        }
    }
}
