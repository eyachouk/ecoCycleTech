package tn.esprit.ecocycletech.Controller.UserManagement;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.tomcat.jni.CertificateVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.ExceptionHandling.UserRegistrationException;
import tn.esprit.ecocycletech.Security.JwtUtils;
import tn.esprit.ecocycletech.Service.UserManagement.IUserService;
import tn.esprit.ecocycletech.Service.UserManagement.RecaptchaService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;
    private final JwtUtils jwtTokenProvider;
    private final RecaptchaService recaptchaService;
    private final GoogleIdTokenVerifier googleIdTokenVerifier;



    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam(value = "photoDeProfil", required = false) MultipartFile file,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("numTelephone") Long numTelephone,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("adresse") String adresse,
            @RequestParam("password") String password,
            @RequestParam("recaptchaToken") String recaptchaToken) {

        Map<String, Object> response = new HashMap<>();

            // Validate reCAPTCHA token first


        try {
            if (!recaptchaService.validateToken(recaptchaToken)) {
                response.put("success", false);
                response.put("error", "reCAPTCHA validation failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            // Validation basique des paramètres
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email is required");
            }
            // Ajouter d'autres validations si nécessaire

            RegisterRequest request = new RegisterRequest();
            request.setNom(nom);
            request.setPrenom(prenom);
            request.setEmail(email);
            request.setUsername(username);
            request.setNumTelephone(numTelephone);
            request.setDateNaissance(LocalDate.parse(dateNaissance));
            request.setAdresse(adresse);
            request.setPassword(password);

            if (file != null && !file.isEmpty()) {
                request.setPhotoDeProfil(file.getBytes());
            }

            User user = userService.registerUser(request);
            UserDetails userDetails = getUserDetails(user);

            String token = jwtTokenProvider.generateToken(userDetails);

            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("token", token);
            response.put("userId", user.getIdUser());

            return ResponseEntity.ok(response);

        } catch (UserRegistrationException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            response.put("field", e.getFieldName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "An unexpected error occurred");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        return ResponseEntity.ok(Map.of("status", "Server is up and running!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            LoginResponse loginResponse = userService.login(request);
            response.put("success", true);
            response.put("data", loginResponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyEmail(@RequestParam("token") String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean verified = userService.verifyEmail(token);
            if (verified) {
                response.put("success", true);
                response.put("message", "Email verified successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("error", "Invalid or expired token");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Verification process failed");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private UserDetails getUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();
    }
    @GetMapping("/oauth2/redirect")
    public void handleRedirect() {
        // This endpoint will be used by the frontend to handle OAuth2 redirects
        // The actual implementation is in the OAuth2AuthenticationSuccessHandler
    }
    @PostMapping("/oauth2/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody Map<String, String> request) {
        try {
            System.out.println("Received Google auth request: " + request);

            // Get the Google ID token from the request
            String idToken = request.get("credential");
            if (idToken == null) {
                System.out.println("No token provided in request");
                return ResponseEntity.badRequest().body(Map.of("error", "No token provided"));
            }

            // Verify the token and extract user info using Google's API or a library
            // This is a simplified example - you should implement proper token verification
            GoogleIdToken.Payload payload = googleIdTokenVerifier.verify(idToken).getPayload();
            if (payload == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid token"));
            }

            // Process user information
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");

            // Find or create user in your system
            User user = userService.findOrCreateGoogleUser(email, name, emailVerified);

            UserDetails userDetails = getUserDetails(user);
            // Generate JWT token
            String jwtToken = jwtTokenProvider.generateToken(userDetails);

            // Return response with token and user info
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("id", user.getIdUser());
            response.put("email", user.getEmail());
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }
    }
    @PostMapping("/oauth2/facebook")
    public ResponseEntity<?> authenticateWithFacebook(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String email = request.get("email");
            String name = request.get("name");

            if (token == null || email == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid request parameters"));
            }

            // Verify the token with Facebook (implementation depends on your setup)
            // For simplicity, we'll assume the token is valid here

            // Find or create user
            User user = userService.findOrCreateFacebookUser(email, name, true);

            UserDetails userDetails = getUserDetails(user);
            String jwtToken = jwtTokenProvider.generateToken(userDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("id", user.getIdUser());
            response.put("email", user.getEmail());
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Authentication failed: " + e.getMessage()));
        }
    }
}