package tn.esprit.ecocycletech.ExceptionHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import tn.esprit.ecocycletech.Security.JwtUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final IUserRepository userRepository;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtUtils jwtUtils, IUserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        String email = extractEmail(oAuth2User, provider);
        String name = extractName(oAuth2User, provider);

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;

        if (userOptional.isEmpty()) {
            // Create new user
            user = new User();
            user.setEmail(email);

            // Split name into first and last name
            String[] nameParts = name.split(" ", 2);
            user.setPrenom(nameParts[0]);
            user.setNom(nameParts.length > 1 ? nameParts[1] : "");

            // Set default username from email
            user.setUsername(email.split("@")[0]);
            user.setRole(UserRole.USER);
            user.setActive(true);
            user.setEmailVerified(true);
            user.setBanned(false);

            // Generate random password for OAuth2 users
            // In a real app, you might want to handle this differently
            String randomPassword = generateRandomPassword();
            user.setPassword(randomPassword);

            userRepository.save(user);
        } else {
            user = userOptional.get();
        }

        // Generate JWT token
        String token = jwtUtils.generateTokenForOAuth2User(user);

        // Build redirect URL with token
        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:4200/oauth2/redirect")
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String extractEmail(OAuth2User oAuth2User, String provider) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("google".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("email");
        }

        return "";
    }

    private String extractName(OAuth2User oAuth2User, String provider) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("google".equals(provider)) {
            return (String) attributes.get("name");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("name");
        }

        return "";
    }

    private String generateRandomPassword() {
        // Simple implementation for example purposes
        // In a real application, use a secure random password generator
        return "oAuth2User" + System.currentTimeMillis();
    }
}