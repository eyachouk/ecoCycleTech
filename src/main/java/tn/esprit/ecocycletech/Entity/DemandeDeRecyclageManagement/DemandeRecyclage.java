package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DemandeRecyclage  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDemandeRecyclage;
    @PastOrPresent
    private Date dateCreationDemandeRecyclage;
    @NotBlank
    @Size(min = 15)
    private String descriptionDemandeRecyclage;
    //pt de collecte mafhemtech kifech lezem tjibha (normalement bech tjibha mel collecte tjib ken id mtaa l point de collecte mbaad tekhdem fel select where idptdecollecte)
    @Min(1)
    private int nbrAppareils;
    private Etat etatDemandeRecyclage;
    @Min(1)
    private double prixDemandeRecyclage;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = true)
    private User user;
    //na7it el fetch li baa3d cascadeType
    @OneToOne(mappedBy = "demandeRecyclage", cascade = CascadeType.ALL)
    private CertificatRecyclage certificatRecyclage;
    @OneToOne(mappedBy = "demandeRecyclage", cascade = CascadeType.ALL)
    private Collecte collecte;

    public byte[] getImageData() {
        return imageData;
    }

    public int getIdDemandeRecyclage() {
        return idDemandeRecyclage;
    }

    public void setIdDemandeRecyclage(int idDemandeRecyclage) {
        this.idDemandeRecyclage = idDemandeRecyclage;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;


    @Transient
    @JsonProperty("imageBase64")
    public String getImageBase64() {
        return this.imageData != null ? Base64.getEncoder().encodeToString(this.imageData) : null;
    }
}
