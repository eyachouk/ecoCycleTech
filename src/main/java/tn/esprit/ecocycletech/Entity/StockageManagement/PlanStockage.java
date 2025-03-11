package tn.esprit.ecocycletech.Entity.StockageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanStockage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlan;
    private String titre;
    private BigDecimal tailleMax;
    private double prix;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "planStockage")
    private List<EspaceStockage> espaces;
}
