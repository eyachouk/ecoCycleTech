package tn.esprit.ecocycletech.Service.StockageManagement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;


@Service
public class GeminiService {

    private final WebClient webClient;
    private final Tika tika;

    @Value("${gemini.apikey}")
    private  String API_KEY;

    public GeminiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent")
                .build();
        this.tika = new Tika();
    }
    public ResponseEntity<String> analyzeText(MultipartFile file) throws IOException {
        try {

            String extractedText = tika.parseToString(file.getInputStream());
            String prompt =  "Analyze the following text for sensitive information (passwords, credit cards, API keys, etc.). " +
                    "Respond ONLY with a pure JSON object, without using any markdown or code block formatting. " +
                    "Respond STRICTLY in JSON format like this: " +
                    "{ \"filename\": \"yourfile\", \"sensitiveDataDetected\": true/false, \"details\": \"short description\" }. " +
                    "DO NOT add any explanation. Only return the JSON.\n" +
                    "Filename: " + file.getOriginalFilename() + "\n" +
                    "Text:\n" + extractedText;

            String requestBody = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        { \"text\": \"" + escapeJson(prompt) + "\" }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";


            String response = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", API_KEY).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String cleanJson = extractJsonFromGeminiResponse(response);
            return ResponseEntity.ok(cleanJson);



        } catch (TikaException e) {
            throw new IOException("Failed to extract text from file", e);
        }
    }

    // Escape " and newlines properly in JSON
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"").replace("\n", "\\n");
    }


    private String extractJsonFromGeminiResponse(String geminiResponse) {
        try {
            // Parse the entire Gemini response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(geminiResponse);

            // Navigate to the actual text content
            String textContent = root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // Remove the ```json and ``` around the real JSON
            if (textContent.startsWith("```json")) {
                textContent = textContent.substring(7); // remove ```json\n
            }
            if (textContent.endsWith("```")) {
                textContent = textContent.substring(0, textContent.length() - 3); // remove \n```
            }
            return textContent.trim(); // clean extra spaces
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract JSON from Gemini response", e);
        }
    }
}