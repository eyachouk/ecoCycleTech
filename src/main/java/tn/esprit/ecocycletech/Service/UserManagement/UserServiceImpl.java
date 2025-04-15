package tn.esprit.ecocycletech.Service.UserManagement;



import lombok.RequiredArgsConstructor;
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
import tn.esprit.ecocycletech.DTO.LoginRequest;
import tn.esprit.ecocycletech.DTO.LoginResponse;
import tn.esprit.ecocycletech.DTO.MailBody;
import tn.esprit.ecocycletech.DTO.RegisterRequest;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
import tn.esprit.ecocycletech.Repository.UserManagement.*;
import tn.esprit.ecocycletech.Security.JwtUtils;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;


    public User registerUser(RegisterRequest request) throws IllegalArgumentException,RuntimeException{
        if (request.getNom() == null || request.getPrenom() == null ||
                request.getEmail() == null || request.getUsername() == null ||
                request.getNumTelephone() == null || request.getDateNaissance() == null ||
                request.getPassword() == null) {
            throw new IllegalArgumentException("All required fields must be provided");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists!");
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
        user.setRole(UserRole.ADMIN);
        user.setActive(true);
        user.setBanned(false);
        // Build and save the user
        return userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request) {
        System.out.println("Attempting login for email: " + request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());

        System.out.println("Retrieving user with email: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + request.getEmail());
                    return new RuntimeException("User not found");
                });

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
