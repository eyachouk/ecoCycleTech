package tn.esprit.ecocycletech.Entity.StockageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.Enumerations.TypeFichier;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Fichier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFichier;
    private String nom;
    private BigDecimal taille;
    @Temporal(TemporalType.DATE)
    private Date dateUpload;
    private String urlStockage;
    @ManyToOne
    private EspaceStockage espace;
    private TypeFichier type;

}
