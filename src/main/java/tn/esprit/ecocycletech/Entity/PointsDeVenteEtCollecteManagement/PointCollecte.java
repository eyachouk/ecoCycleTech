package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Entity.Enumerations.Disponibilite;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PointCollecte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPointCollecte;
    private String adressePointCollecte;
    private int numTelephonePointCollecte;
    private String emailPointCollecte;
    //tnajem trod l'heureOuverture +heureFermeture string wala int
    private LocalTime heureOuverturePointCollecte;
    private LocalTime heureFermeturePointCollecte;
    private int capacitePointCollecte;
    private Disponibilite disponibilitePointCollecte;
    @OneToMany(mappedBy = "pointCollecte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Collecte> collecteList;
}
