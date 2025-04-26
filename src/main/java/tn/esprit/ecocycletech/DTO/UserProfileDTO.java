// src/main/java/…/dto/UserProfileDTO.java
package tn.esprit.ecocycletech.DTO;

import tn.esprit.ecocycletech.Entity.UserManagement.User;
import lombok.Data;

@Data
public class UserProfileDTO {
    private Integer id;          //  <-- indispensable !
    private String  nom;
    private String  prenom;
    private String  email;
    private String  username;
    private Long    numTelephone;
    private String  adresse;
    private byte[]  photoDeProfil;   // facultatif

    public static UserProfileDTO from(User u) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.id            = u.getIdUser();
        dto.nom           = u.getNom();
        dto.prenom        = u.getPrenom();
        dto.email         = u.getEmail();
        dto.username      = u.getUsername();
        dto.numTelephone  = u.getNumTelephone();
        dto.adresse       = u.getAdresse();
        dto.photoDeProfil = u.getPhotoDeProfil();
        return dto;
    }
}
