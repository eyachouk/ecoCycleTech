package tn.esprit.ecocycletech.Entity.EvenementsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketEvenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTicketEvenement;
    @ManyToOne
    @JoinColumn(name = "evenement", nullable = false)
    private Evenement evenement;
    @Temporal(TemporalType.DATE)
    private Date dateAchat;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
}
