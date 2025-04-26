package tn.esprit.ecocycletech.Controller.UserManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ecocycletech.DTO.UserProfileDTO;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Service.UserManagement.IUserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService service;

    /** Renvoie le profil de l’utilisateur connecté (ou d’un autre si ADMIN) */
    @GetMapping("{email:.+}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable String email,
                                                     Authentication auth) {
        /* ②  rejeter la requête si l’utilisateur n’est pas connecté */
        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            return ResponseEntity.status(401).build();
        }

        /* ③  principal := UserDetails → récupérer l’email */
        String connectedEmail =
                auth.getName();                     // ← renvoie le username (=email)

        boolean isAdmin  = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isMyself = connectedEmail.equalsIgnoreCase(email);

        if (!isAdmin && !isMyself)
            return ResponseEntity.status(403).build();

        return service.getByEmail(email)
                .map(UserProfileDTO::from)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
