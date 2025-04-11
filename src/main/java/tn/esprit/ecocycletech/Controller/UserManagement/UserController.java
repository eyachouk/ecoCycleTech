package tn.esprit.ecocycletech.Controller.UserManagement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Service.UserManagement.IUserService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private IUserService userService;


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("Received request: " + request);
        return ResponseEntity.ok(userService.registerUser(request));
    }
    @GetMapping("/ping")
    public String ping() {
        return "Server is up and running!";
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("Login endpoint hit for email: " + request.getEmail());
        return ResponseEntity.ok(userService.login(request));
    }
}
