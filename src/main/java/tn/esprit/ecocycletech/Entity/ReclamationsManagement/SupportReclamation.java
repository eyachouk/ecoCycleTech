package tn.esprit.ecocycletech.Entity.ReclamationsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupportReclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSupportReclamation;
    private int idResponsable;
    private String nomResponsable;
    @Temporal(TemporalType.DATE)
    private Date dateOuvertureSupport;
    @Temporal(TemporalType.DATE)
    private Date dateClotureSupport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reclamation", referencedColumnName = "idReclamation")
    private Reclamation reclamation;
}
