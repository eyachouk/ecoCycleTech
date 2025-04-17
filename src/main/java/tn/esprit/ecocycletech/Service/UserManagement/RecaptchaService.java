package tn.esprit.ecocycletech.Service.UserManagement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateToken(String recaptchaToken) {
        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", recaptchaToken);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map> recaptchaResponseEntity =
                    restTemplate.postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL, body, Map.class);

            Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

            if(responseBody == null || !responseBody.containsKey("success")) {
                return false;
            }

            return (Boolean)responseBody.get("success");
        } catch (Exception e) {
            System.err.println("Error validating reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}