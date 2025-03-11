package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DemandeRecyclage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDemandeRecyclage;
    private Date dateCreationDemandeRecyclage;
    private String descriptionDemandeRecyclage;
    //pt de collecte mafhemtech kifech lezem tjibha (normalement bech tjibha mel collecte tjib ken id mtaa l point de collecte mbaad tekhdem fel select where idptdecollecte)
    private int nbrAppareils;
    private Etat etatDemandeRecyclage;
    private double prixDemandeRecyclage;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    @OneToOne(mappedBy = "demandeRecyclage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CertificatRecyclage certificatRecyclage;
    @OneToOne(mappedBy = "demandeRecyclage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collecte collecte;
}
