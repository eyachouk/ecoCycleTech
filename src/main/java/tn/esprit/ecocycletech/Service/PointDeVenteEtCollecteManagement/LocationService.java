package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class LocationService {
    private static final String GEOCODING_API = "https://nominatim.openstreetmap.org/search?format=json&q=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Coordinates convertAddressToCoordinates(String address) throws Exception {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = GEOCODING_API + encodedAddress;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "EcoCycleTech/1.0")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode[] results = objectMapper.readValue(response.body(), JsonNode[].class);

        if (results.length == 0) {
            throw new RuntimeException("No coordinates found for address: " + address);
        }

        return new Coordinates(
                results[0].get("lat").asDouble(),
                results[0].get("lon").asDouble()
        );
    }

    @Data
    public static class Coordinates {
        private final double latitude;
        private final double longitude;
    }
}