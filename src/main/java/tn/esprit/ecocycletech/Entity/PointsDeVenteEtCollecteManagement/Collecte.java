package tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Collecte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCollecte;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCollecte;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime heureDebutCollecte;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime heureFinCollecte;
    private boolean isScheduled;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeRecyclageCollecte", referencedColumnName = "idDemandeRecyclage")
    @JsonIgnore
    private DemandeRecyclage demandeRecyclage;
    @ManyToOne
    @JoinColumn(name = "idPointCollecte", nullable = false)
    @JsonIgnore
    private PointCollecte pointCollecte;
    @ManyToOne
    @JoinColumn(name = "idVehicule", nullable = false)
    @JsonIgnore
    private Vehicule vehicule;

}
