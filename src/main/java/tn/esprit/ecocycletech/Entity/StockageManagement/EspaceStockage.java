package tn.esprit.ecocycletech.Entity.StockageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EspaceStockage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEspace;
    private BigDecimal usedTaille;
    private double prix;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "espace")
    private List<Fichier> fichiers;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "espace")
    private User user;
}
