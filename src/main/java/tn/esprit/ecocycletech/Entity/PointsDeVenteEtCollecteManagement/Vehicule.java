package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVehicule;
    private String marqueVehicule;
    private String modeleVehicule;
    private String nomChauffeur;
    private int numTelephoneChauffeur;
    private int capaciteVehicule;
    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Collecte> collecteList;

}
