package tn.esprit.ecocycletech.Entity.UserManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private long numTelephone;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String adresse;
    private int role;
    private String photoDeProfil;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reclamation> reclamationList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommandeReparation> commandeReparationList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketEvenement> ticketEvenementList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DemandeRecyclage> demandeRecyclageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Avis> avisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reservation> reservationList;
    @OneToOne
    private EspaceStockage espace;
}
