package tn.esprit.ecocycletech.Entity.AppareilsManagement;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Avis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAvis;

    private String contenu;

    @ManyToOne
    private Appareil appareil;
    @ManyToOne
    private User user;
    private int rating; // Rating between 1 and 5

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Appareil getAppareil() {
        return appareil;
    }

    public void setAppareil(Appareil appareil) {
        this.appareil = appareil;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
