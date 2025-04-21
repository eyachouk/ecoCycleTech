package tn.esprit.ecocycletech.Entity.AppareilsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
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
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reservation")
    private List<Appareil> panier;
}
