package tn.esprit.ecocycletech.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String nom;
    private String prenom;
    private Long numTelephone;
    private String adresse;
    private byte[] photoDeProfil;
}