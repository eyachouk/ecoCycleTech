package tn.esprit.ecocycletech.Entity.AppareilsManagement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.Enumerations.EtatAppareil;

import java.io.Serializable;
import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appareil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppareil;
    private String nom;
    private String categorie;
    private EtatAppareil etatAppareil;
    private String marque;
    private double prix;
    private String description;
    private String imageurl;

    @OneToMany(mappedBy = "appareil")
    private List<Avis> Avis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "idReservation"
    )
    private Reservation reservation;

    public int getIdAppareil() {
        return idAppareil;
    }

    public void setIdAppareil(int idAppareil) {
        this.idAppareil = idAppareil;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getEtatAppareil() {
        return etatAppareil != null ? etatAppareil.name() : null; // Converts to String
    }


    public void setEtatAppareil(String etatAppareilString) {
        this.etatAppareil = EtatAppareil.fromString(etatAppareilString);
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public List<Avis> getAvis() {
        return Avis;
    }

    public void setAvis(List<Avis> avis) {
        Avis = avis;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Reservation getReservation() {
        return reservation;
    }


    public void setReservation(Reservation reservation) {
        // Prevent infinite loop
        if (this.reservation == reservation) {
            return;
        }

        // Remove from old reservation
        Reservation oldReservation = this.reservation;
        if (oldReservation != null) {
            oldReservation.removeAppareil(this);
        }

        // Set new reservation
        this.reservation = reservation;

        // Add to new reservation
        if (reservation != null) {
            reservation.addAppareil(this);
        }
    }
}
