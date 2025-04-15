package tn.esprit.ecocycletech.DTO;

import lombok.*;
import tn.esprit.ecocycletech.Entity.Enumerations.UserRole;

import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private Long numTelephone;
    private LocalDate dateNaissance;
    private String adresse;
    private String password;
    private String photoDeProfil; // Optional
}