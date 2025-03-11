package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Collecte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCollecte;
    @Temporal(TemporalType.DATE)
    private Date dateCollecte;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeRecyclageCollecte", referencedColumnName = "idDemandeRecyclage")
    private DemandeRecyclage demandeRecyclage;
    @ManyToOne
    @JoinColumn(name = "idPointCollecte", nullable = false)
    private PointCollecte pointCollecte;
    @ManyToOne
    @JoinColumn(name = "idVehicule", nullable = false)
    private Vehicule vehicule;

}
