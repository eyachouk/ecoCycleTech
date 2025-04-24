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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.DTO.UserUpdateRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.ExceptionHandling.UserRegistrationException;
import tn.esprit.ecocycletech.Security.JwtUtils;
import tn.esprit.ecocycletech.Service.UserManagement.IUserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;
    private final JwtUtils jwtTokenProvider;
    //private final RecaptchaService recaptchaService;
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
            @RequestParam("password") String password) {

        Map<String, Object> response = new HashMap<>();

            // Validate reCAPTCHA token first


        try {

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
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            LoginResponse loginResponse = userService.login(request);

            // Ensure these fields are included in the response
            response.put("token", loginResponse.getToken());
            response.put("email", loginResponse.getEmail());
            response.put("role", loginResponse.getRole()); // Convert enum to string
            response.put("id", loginResponse.getId());

            // Add any other fields your frontend expects
            response.put("username", loginResponse.getUsername());

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
            response.put("username", user.getUsername()); // Add if available

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

    @GetMapping("/fetch/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable int userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable int userId,
            @RequestParam(value = "photoDeProfil", required = false) MultipartFile file,
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "prenom", required = false) String prenom,
            @RequestParam(value = "numTelephone", required = false) Long numTelephone,
            @RequestParam(value = "adresse", required = false) String adresse) {

        try {
            // Valider que l'utilisateur connecté est bien celui qu'on met à jour
            // ou qu'il s'agit d'un admin (à implémenter selon votre logique d'authentification)

            UserUpdateRequest request = new UserUpdateRequest();
            request.setNom(nom);
            request.setPrenom(prenom);
            request.setNumTelephone(numTelephone);
            request.setAdresse(adresse);

            if (file != null && !file.isEmpty()) {
                request.setPhotoDeProfil(file.getBytes());
            }

            User updatedUser = userService.updateUserProfile(userId, request);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Profile updated successfully");
            response.put("user", updatedUser);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "error", "Failed to process image"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            String oldToken = jwtTokenProvider.getTokenFromRequest(request);
            if (oldToken != null && jwtTokenProvider.canTokenBeRefreshed(oldToken)) {
                String username = jwtTokenProvider.extractUsername(oldToken);
                User user = userService.loadUserByUsername(username);
                UserDetails userDetails = getUserDetails(user);
                String newToken = jwtTokenProvider.generateToken(userDetails);

                return ResponseEntity.ok(new LoginResponse(
                        newToken,
                        "Bearer",
                        user.getIdUser(),
                        user.getEmail(),
                        user.getRole().toString(),
                        user.getUsername()

                ));
            }
            return ResponseEntity.badRequest().body("Invalid refresh request");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token refresh failed");
        }
    }
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String token = jwtTokenProvider.getTokenFromRequest(request);
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                response.put("valid", false);
                return ResponseEntity.ok(response);
            }

            String username = jwtTokenProvider.extractUsername(token);
            User user = userService.loadUserByUsername(username);

            response.put("valid", true);
            response.put("user", Map.of(
                    "id", user.getIdUser(),
                    "email", user.getEmail(),
                    "role", user.getRole().name(),
                    "username", user.getUsername()
            ));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }
    }
    //backoffice
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}