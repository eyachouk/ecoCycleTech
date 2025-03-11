package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificatRecyclage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCertificatRecyclage;
    @Temporal(TemporalType.DATE)
    private Date dateEmissionCertificat;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "demandeRecyclageCertificat", referencedColumnName = "idDemandeRecyclage")
    private DemandeRecyclage demandeRecyclage;
}
