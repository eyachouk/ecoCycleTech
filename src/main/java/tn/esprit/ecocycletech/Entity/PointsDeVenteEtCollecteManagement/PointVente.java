package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PointVente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPointVente;
    private String adressePointVente;
    private LocalTime heureOuverturePointVente;
    private LocalTime heureFermeturePointVente;
    private int numTelephonePointVente;
    private String emailPointVente;
    @OneToMany(mappedBy = "pointVente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommandeReparation> commandeReparationList;

}
