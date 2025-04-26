
package tn.esprit.ecocycletech.Service.UserManagement;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.ecocycletech.DTO.*;
import tn.esprit.ecocycletech.Entity.Enumerations.UserStatus;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Entity.UserManagement.VerificationMailEvent;
import tn.esprit.ecocycletech.Entity.UserManagement.VerificationToken;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
import tn.esprit.ecocycletech.ExceptionHandling.UserRegistrationException;
import tn.esprit.ecocycletech.Repository.UserManagement.*;
import tn.esprit.ecocycletech.Security.JwtUtils;
import tn.esprit.ecocycletech.Security.UserDetailsServiceImpl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final IVerificationTokenRepository tokenRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final ApplicationEventPublisher publisher;   // <-- add


    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:4200}")
    private String baseUrl;

    // @Transactional
    public User registerUser(RegisterRequest request) {
        validateRegisterRequest(request);

        // Save user and create token inside a transaction
        User savedUser = createUserAndToken(request);

        // Send email outside transaction
        publisher.publishEvent(new VerificationMailEvent(savedUser.getIdUser()));

        return savedUser;
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getNom() == null || request.getPrenom() == null || request.getEmail() == null ||
                request.getUsername() == null || request.getNumTelephone() == null ||
                request.getDateNaissance() == null || request.getPassword() == null) {
            throw new UserRegistrationException("All required fields must be provided", "general");
        }

        boolean emailExists = userRepository.existsByEmail(request.getEmail());
        boolean usernameExists = userRepository.existsByUsername(request.getUsername());

        if (emailExists && usernameExists) {
            throw new UserRegistrationException("Email and username already exist", "both");
        }
        if (emailExists) {
            throw new UserRegistrationException("Email already exists", "email");
        }
        if (usernameExists) {
            throw new UserRegistrationException("Username already exists", "username");
        }
    }
    //@Transactional
    public User createUserAndToken(RegisterRequest request) {
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setNumTelephone(request.getNumTelephone());
        user.setDateNaissance(request.getDateNaissance());
        user.setAdresse(request.getAdresse());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhotoDeProfil(request.getPhotoDeProfil());
        user.setRole(UserRole.USER);
        user.setActive(false);
        user.setBanned(false);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        VerificationToken token = new VerificationToken(savedUser);
        tokenRepository.save(token);

        return savedUser;
    }
    private String getTokenForUser(User user) {
        return tokenRepository.findByUser(user)
                .map(VerificationToken::getToken)
                .orElseThrow(() -> new RuntimeException("Token not found for user"));
    }
    public String findVerificationToken(User user) {
        return getTokenForUser(user);   // re-use your existing private method
    }


    public void sendVerificationEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Complete Registration for EcoCycleTech");
        message.setText("Hello " + user.getPrenom() + ",\n\n" +
                "Thank you for registering with EcoCycleTech. Please click on the link below to verify your email:\n\n" +
                baseUrl + "/verify-email?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8) + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "Regards,\nEcoCycleTech Team");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }

    }

    //@Transactional
    public LoginResponse verifyEmail(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(()->new RuntimeException("Invalid Token"));

        if (verificationToken == null || verificationToken.isExpired()) {
            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        System.out.println("DB has token   : " +
                tokenRepository.findByUser(user).map(VerificationToken::getToken));
        System.out.println("Request token  : " + token);
        user.setEmailVerified(true);
        user.setActive(true);
        userRepository.save(user);

        // Delete the token after use
        tokenRepository.delete(verificationToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwt = jwtUtils.generateToken(userDetails);

        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(user.getIdUser())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public User findOrCreateGoogleUser(String email, String name, boolean emailVerified) {
        /*if (!emailVerified) {
            throw new RuntimeException("Google email not verified");
        }*/

        // Try to find existing user
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Update user details if they've changed in Google
            if (!user.getNom().equals(name)) {
                user.setNom(name);
                userRepository.save(user);
            }

            return user;
        } else {

            // Create new user
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setNom(name);
            newUser.setDateNaissance(new Date().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            newUser.setAdresse("");
            newUser.setPrenom("");
            newUser.setActive(true);
            newUser.setUsername(email.split("@")[0]);
            newUser.setNumTelephone(0L);
            newUser.setEmailVerified(true);



            // Set a random password (won't be used for Google login)
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

            // Set default role (you might want to customize this)
            newUser.setRole(UserRole.USER);

            // Mark as Google-authenticated user
            //  newUser.setAuthProvider(AuthProvider.GOOGLE);

            return userRepository.save(newUser);
        }
    }

    @Override
    public User findOrCreateFacebookUser(String email, String name, boolean emailVerified) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Facebook email is required");
        }

        // Try to find existing user
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Update user details if they've changed
            if (!user.getNom().equals(name)) {
                user.setNom(name);
                userRepository.save(user);
            }

            return user;
        } else {
            // Create new user
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setNom(name);
            newUser.setPrenom(""); // You might want to parse the full name
            newUser.setUsername(email.split("@")[0]); // Use part of email as username
            newUser.setActive(true);
            newUser.setEmailVerified(true);

            // Set a random password (won't be used for Facebook login)
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

            // Set default role
            newUser.setRole(UserRole.USER);

            return userRepository.save(newUser);
        }
    }

    // You might want to update the login method to check if email is verified
    public LoginResponse login(LoginRequest request) {
        System.out.println("Attempting login for email: " + request.getEmail());

        // First check if the user exists and is verified
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        System.out.println("kbal if user.isEmailVerified");
        // Enforce email verification
        if (!user.isEmailVerified() && user.getRole().equals(UserRole.USER)) {
            throw new RuntimeException("Please verify your email first");
        }

        System.out.println("mbaad if user.isEmailVerified");

        if (!user.isActive() && user.getRole().equals(UserRole.USER)) {
            throw new RuntimeException("Account is inactive");
        }

        // Optional: Check if email is verified
            /*
            if (!user.isEmailVerified()) {
                throw new RuntimeException("Email not verified. Please check your email for verification link.");
            }
            */
        System.out.println("*****************kbal auhenticate");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        System.out.println("*****************baad auhenticate");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

        System.out.println("User retrieved: " + user.getEmail());

        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .id(user.getIdUser())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
    //@Transactional
    public User updateUserProfile(int userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Mettre à jour uniquement les champs non-null fournis dans la requête
        if (request.getNom() != null && !request.getNom().isEmpty()) {
            user.setNom(request.getNom());
        }

        if (request.getPrenom() != null && !request.getPrenom().isEmpty()) {
            user.setPrenom(request.getPrenom());
        }

        if (request.getNumTelephone() != null) {
            user.setNumTelephone(request.getNumTelephone());
        }

        if (request.getAdresse() != null) {
            user.setAdresse(request.getAdresse());
        }

        // Mettre à jour la photo de profil si une nouvelle est fournie
        if (request.getPhotoDeProfil() != null) {
            user.setPhotoDeProfil(request.getPhotoDeProfil());
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findByIdUser(userId);
    }

    @Override
    public User loadUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(int id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User changeUserStatus(int id, UserStatus userStatus) {
        // 1. Find the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // 2. Check if the status change is valid (optional business logic)
        if (user.getStatus() == userStatus) {
            throw new IllegalStateException("User already has status: " + userStatus);
        }

        // Example of additional business rules:
        if (userStatus == UserStatus.BANNED && user.getRole() == UserRole.ADMIN) {
            throw new IllegalStateException("Cannot ban an admin user");
        }

        // 3. Update the status
        user.setStatus(userStatus);

        // 4. Save and return the updated user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
