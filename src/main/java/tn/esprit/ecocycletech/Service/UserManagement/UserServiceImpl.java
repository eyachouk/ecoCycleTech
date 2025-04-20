package tn.esprit.ecocycletech.Service.UserManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
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
import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.MailBody;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Entity.UserManagement.VerificationToken;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
import tn.esprit.ecocycletech.ExceptionHandling.UserRegistrationException;
import tn.esprit.ecocycletech.Repository.UserManagement.*;
import tn.esprit.ecocycletech.Security.JwtUtils;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final IVerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:4200}")
    private String baseUrl;

    @Transactional
    public User registerUser(RegisterRequest request) throws IllegalArgumentException,RuntimeException{
        if (request.getNom() == null || request.getPrenom() == null ||
                request.getEmail() == null || request.getUsername() == null ||
                request.getNumTelephone() == null || request.getDateNaissance() == null ||
                request.getPassword() == null) {
            throw new UserRegistrationException("All required fields must be provided", "general");
        }

        // Vérification email et username
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
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setNumTelephone(request.getNumTelephone());
        user.setDateNaissance(
                request.getDateNaissance()
        );
        user.setAdresse(request.getAdresse());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhotoDeProfil(request.getPhotoDeProfil());
        // Set default values
        user.setRole(UserRole.USER);
        user.setActive(true);
        user.setBanned(false);
        user.setEmailVerified(false); // Set email as not verified

        // Save the user
        User savedUser = userRepository.save(user);

        // Create verification token
        VerificationToken token = new VerificationToken(savedUser);
        tokenRepository.save(token);

        // Send verification email
        sendVerificationEmail(savedUser, token.getToken());

        return savedUser;
    }

    private void sendVerificationEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Complete Registration for EcoCycleTech");
        message.setText("Hello " + user.getPrenom() + ",\n\n" +
                "Thank you for registering with EcoCycleTech. Please click on the link below to verify your email:\n\n" +
                baseUrl + "/verify-email?token=" + token + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "Regards,\nEcoCycleTech Team");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
    }

    @Transactional
    public boolean verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (verificationToken == null || verificationToken.isExpired()) {
            return false;
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        // Delete the token after use
        tokenRepository.delete(verificationToken);

        return true;
    }

    @Override
    public User findOrCreateGoogleUser(String email, String name, boolean emailVerified) {
        if (!emailVerified) {
            throw new RuntimeException("Google email not verified");
        }

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
            newUser.setActive(true);

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
            newUser.setEmailVerified(emailVerified);

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
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + request.getEmail());
                    return new RuntimeException("User not found");
                });

        // Optional: Check if email is verified
        /*
        if (!user.isEmailVerified()) {
            throw new RuntimeException("Email not verified. Please check your email for verification link.");
        }
        */

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

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
}