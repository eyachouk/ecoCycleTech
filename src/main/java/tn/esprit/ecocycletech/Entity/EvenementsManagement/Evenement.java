package tn.esprit.ecocycletech.Entity.EvenementsManagement;

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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvenement;
    private String nomEvenement;
    @Temporal(TemporalType.DATE)
    private Date dateEvenement;
    private String localisationEvenement;
    private int nbrPlacesEvenement;
    private double prixEvenement;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TicketEvenement> ticketEvenementList;

}
