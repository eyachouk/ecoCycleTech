package tn.esprit.ecocycletech.Entity.CommandesManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.Enumerations.TypeAppareil;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointVente;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandeReparation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCommandeReparation;
    private String titreCommande;
    private TypeAppareil typeAppareil;
    private String descriptionCommande;
    private String typeCollecteCommande;
    //tokeed relation maa l pt de vente (many to one)
    @Temporal(TemporalType.DATE)
    private Date dateCreationCommande;
    private Etat etatCommande;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    @OneToOne(mappedBy = "commandeReparation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RendezVous rendezVous;
    @ManyToOne
    @JoinColumn(name = "idPointVente", nullable = false)
    private PointVente pointVente;

}
