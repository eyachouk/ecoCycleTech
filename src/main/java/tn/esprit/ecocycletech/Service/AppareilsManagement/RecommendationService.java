package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@Service
public class RecommendationService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String getRecommendations(String query) {
        String url = "http://localhost:8090/ecoCycleTech/api/recommend";
        Map<String, String> requestBody = Map.of("query", query);
        return restTemplate.postForObject(url, requestBody, String.class);
    }
}

