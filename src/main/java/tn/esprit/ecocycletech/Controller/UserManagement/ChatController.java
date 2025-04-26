package tn.esprit.ecocycletech.Controller.UserManagement;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.DTO.ChatRequest;
import tn.esprit.ecocycletech.Service.UserManagement.OpenAiService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") // allow Angular frontend to connect
public class ChatController {

    @Autowired
    private OpenAiService openAIService;

    @PostMapping
    public String chat(@RequestBody tn.esprit.ecocycletech.DTO.ChatRequest request) {
        return openAIService.chatWithAssistant(request.getMessage());
    }
}