package tn.esprit.ecocycletech.Service.UserManagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class OpenAiService {
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.assistant.id}")
    private String assistantId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chatWithAssistant(String userMessage) {
        try {
            // Start thread
            JSONObject thread = createThread();
            String threadId = thread.getString("id");

            // Add user message
            createMessage(threadId, userMessage);

            // Run assistant
            JSONObject run = createRun(threadId);
            String runId = run.getString("id");

            // ⭐️ Wait for the run to complete
            while (true) {
                JSONObject runStatus = retrieveRun(threadId, runId);
                String status = runStatus.getString("status");

                if ("completed".equals(status)) {
                    break;
                } else if ("failed".equals(status) || "cancelled".equals(status)) {
                    throw new RuntimeException("Run failed or was cancelled");
                }

                // Sleep 1 second to avoid spamming
                Thread.sleep(1000);
            }

            // Now fetch the real assistant reply
            JSONObject reply = fetchReply(threadId);

            return reply.getJSONArray("data")
                    .getJSONObject(0)
                    .getJSONArray("content")
                    .getJSONObject(0)
                    .getJSONObject("text")
                    .getString("value");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    private JSONObject retrieveRun(String threadId, String runId) {
        HttpHeaders headers = createHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/threads/" + threadId + "/runs/" + runId,
                HttpMethod.GET,
                entity,
                String.class
        );

        return new JSONObject(response.getBody());
    }


    private JSONObject createThread() {
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/threads", entity, String.class);

        return new JSONObject(response.getBody());
    }

    private void createMessage(String threadId, String userMessage) {
        HttpHeaders headers = createHeaders();

        JSONObject body = new JSONObject();
        body.put("role", "user");
        body.put("content", userMessage);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        restTemplate.postForEntity(
                "https://api.openai.com/v1/threads/" + threadId + "/messages",
                entity,
                String.class
        );
    }

    private JSONObject createRun(String threadId) {
        HttpHeaders headers = createHeaders();

        JSONObject body = new JSONObject();
        body.put("assistant_id", assistantId);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/threads/" + threadId + "/runs",
                entity,
                String.class);

        return new JSONObject(response.getBody());
    }

    private JSONObject fetchReply(String threadId) {
        HttpHeaders headers = createHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/threads/" + threadId + "/messages",
                HttpMethod.GET,
                entity,
                String.class);

        return new JSONObject(response.getBody());
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("OpenAI-Beta", "assistants=v2");
        return headers;
    }

}
