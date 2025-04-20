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
<<<<<<< HEAD
    private int idUser;
=======
    private Integer idUser;
>>>>>>> e88a1f3 (update)
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
<<<<<<< HEAD
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reclamation> reclamationList;
=======
   // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private List<Reclamation> reclamationList;
>>>>>>> e88a1f3 (update)
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
<<<<<<< HEAD
    @OneToOne
    private EspaceStockage espace;
=======

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(long numTelephone) {
        this.numTelephone = numTelephone;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhotoDeProfil() {
        return photoDeProfil;
    }

    public void setPhotoDeProfil(String photoDeProfil) {
        this.photoDeProfil = photoDeProfil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CommandeReparation> getCommandeReparationList() {
        return commandeReparationList;
    }

    public void setCommandeReparationList(List<CommandeReparation> commandeReparationList) {
        this.commandeReparationList = commandeReparationList;
    }

    public List<TicketEvenement> getTicketEvenementList() {
        return ticketEvenementList;
    }

    public void setTicketEvenementList(List<TicketEvenement> ticketEvenementList) {
        this.ticketEvenementList = ticketEvenementList;
    }

    public List<DemandeRecyclage> getDemandeRecyclageList() {
        return demandeRecyclageList;
    }

    public void setDemandeRecyclageList(List<DemandeRecyclage> demandeRecyclageList) {
        this.demandeRecyclageList = demandeRecyclageList;
    }

    public List<Avis> getAvisList() {
        return avisList;
    }

    public void setAvisList(List<Avis> avisList) {
        this.avisList = avisList;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public EspaceStockage getEspace() {
        return espace;
    }

    public void setEspace(EspaceStockage espace) {
        this.espace = espace;
    }

    @OneToOne
    private EspaceStockage espace;

    public Integer getIdUser() {
        return idUser;
    }
>>>>>>> e88a1f3 (update)
}
