package tn.esprit.ecocycletech.Entity.EvenementsManagement;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JoinColumn(name = "evenement")
    private Evenement evenement;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAchat;
    private String qrCodeUrl;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = true )
    private User user;
}
