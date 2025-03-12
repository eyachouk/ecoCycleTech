package tn.esprit.ecocycletech.Entity.ReclamationsManagement;

import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReclamation;
    private String titreReclamation;
    private String descriptionReclamation;
    @Temporal(TemporalType.DATE)
    private Date dateReclamation;
    private Etat etatReclamation;
    @OneToOne(mappedBy = "reclamation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SupportReclamation supportReclamation;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

}
