package tn.esprit.ecocycletech.Entity.CommandesManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.SupportReclamation;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRendezVous;
    @Temporal(TemporalType.DATE)
    private Date dateRendezVous;
    //relation maa l pt de vente
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commandeReparation", referencedColumnName = "idCommandeReparation")
    private CommandeReparation commandeReparation;

}
