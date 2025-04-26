package tn.esprit.ecocycletech.Entity.UserManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore  // Add this annotation
    private List<Reclamation> reclamationList;
    @JsonIgnore  // Add this annotation
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Add this annotation
    private List<CommandeReparation> commandeReparationList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Add this annotation
    private List<TicketEvenement> ticketEvenementList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Add this annotation
    private List<DemandeRecyclage> demandeRecyclageList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore  // Add this annotation
    private List<Avis> avisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore  // Add this annotation
    private List<Reservation> reservationList;
    @OneToOne
    private EspaceStockage espace;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public List<Reclamation> getReclamationList() {
        return reclamationList;
    }

    public void setReclamationList(List<Reclamation> reclamationList) {
        this.reclamationList = reclamationList;
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
}
