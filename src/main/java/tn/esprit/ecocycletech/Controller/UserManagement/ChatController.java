package tn.esprit.ecocycletech.Controller.UserManagement;



import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.DTO.ChatRequest;
import tn.esprit.ecocycletech.DTO.ChatResponse;
import tn.esprit.ecocycletech.Service.UserManagement.OpenAiService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ChatController {


    private final OpenAiService openAIService;
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    /*@PostMapping("/ask")
    public String chat(@RequestBody tn.esprit.ecocycletech.DTO.ChatRequest request) {
        return openAIService.chatWithAssistant(request.getMessage());
    }*/
    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> chat(@RequestBody @Valid ChatRequest request) {
        try {
            logger.info("Received chat request: {}", request.getMessage());
            String response = openAIService.chatWithAssistant(request.getMessage());
            ChatResponse chatResponse = new ChatResponse(response);
            logger.info("Chat response: {}", response);
            return new ResponseEntity<>(chatResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ChatResponse("Error processing request"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}