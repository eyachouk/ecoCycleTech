package tn.esprit.ecocycletech.Entity.EvenementsManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEvenement;
    private String localisationEvenement;
    public String provenanceEvenement;
    private int nbrPlacesEvenement;
    private double prixEvenement;
    public String aftermovie;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TicketEvenement> ticketEvenementList;


}
