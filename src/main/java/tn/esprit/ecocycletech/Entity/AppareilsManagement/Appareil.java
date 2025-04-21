package tn.esprit.ecocycletech.Entity.AppareilsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.Enumerations.EtatAppareil;

import java.io.Serializable;
import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appareil implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppareil;

    private String nom;
    private String categorie;
    private EtatAppareil etatAppareil;//kenet esmha type walet esmha etat
    private String marque;
    private int quantite;
    private double prix;
    private String description;
    private String imageurl;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "appareil")
    private List<Avis> Avis;

    @ManyToOne(cascade = CascadeType.ALL)
    private Reservation reservation;


}
