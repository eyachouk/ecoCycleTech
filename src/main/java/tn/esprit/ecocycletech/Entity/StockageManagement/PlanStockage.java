package tn.esprit.ecocycletech.Entity.StockageManagement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private long idPlan;
    private String titre;
    private BigDecimal tailleMax;
    private double prix;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},mappedBy = "planStockage")
    @JsonManagedReference
    private List<EspaceStockage> espaces;


    public long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(long idPlan) {
        this.idPlan = idPlan;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public BigDecimal getTailleMax() {
        return tailleMax;
    }

    public void setTailleMax(BigDecimal tailleMax) {
        this.tailleMax = tailleMax;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public List<EspaceStockage> getEspaces() {
        return espaces;
    }

    public void setEspaces(List<EspaceStockage> espaces) {
        this.espaces = espaces;
    }
}
