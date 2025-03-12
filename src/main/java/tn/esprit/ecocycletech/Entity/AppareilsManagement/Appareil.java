package tn.esprit.ecocycletech.Entity.AppareilsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.Enumerations.EtatAppareil;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appareil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppareil;
    @NotNull
    private String nom;
    private String categorie;
    private EtatAppareil etatAppareil;//kenet esmha type walet esmha etat
    private String marque;
    private int quantite;
    private double prix;
    private String description;
    private String imageurl;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "appareil")
    private List<Avis> Avis;

    @ManyToOne(cascade = CascadeType.ALL)
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

    public EtatAppareil getEtatAppareil() {
        return etatAppareil;
    }

    public void setEtatAppareil(EtatAppareil etatAppareil) {
        this.etatAppareil = etatAppareil;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
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
        this.reservation = reservation;
    }
}
