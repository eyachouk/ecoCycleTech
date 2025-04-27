package tn.esprit.ecocycletech.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
public class ChatRequest {
    @NotBlank(message = "Message cannot be empty")
    private String message;

}

