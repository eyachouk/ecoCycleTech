package tn.esprit.ecocycletech.Entity.AppareilsManagement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservation;

    private String statut;
    private double total;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // In Reservation.java
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.LAZY) // Changed to LAZY
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "idAppareil"
    )
    private List<Appareil> panier;


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Appareil> getPanier() {
        return panier;
    }


    public void addAppareil(Appareil appareil) {
        if (panier == null) {
            panier = new ArrayList<>();
        }
        panier.add(appareil);
        appareil.setReservation(this); // This maintains both sides of the relationship
    }

    public void removeAppareil(Appareil appareil) {
        if (panier != null) {
            panier.remove(appareil);
            appareil.setReservation(null); // This maintains both sides of the relationship
        }
    }

    // Remove the setPanier() method or make it private to prevent misuse
    public void setPanier(List<Appareil> panier) {
        this.panier = panier;
    }

}

