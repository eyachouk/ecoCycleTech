package tn.esprit.ecocycletech.Entity.StockageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;

    private StatutEspace statut;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "espace")
    private List<Fichier> fichiers;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "espace")
    private User user;
    @ManyToOne
    private PlanStockage planStockage;


    public long getIdEspace() {
        return idEspace;
    }

    public void setIdEspace(long idEspace) {
        this.idEspace = idEspace;
    }

    public BigDecimal getUsedTaille() {
        return usedTaille;
    }

    public void setUsedTaille(BigDecimal usedTaille) {
        this.usedTaille = usedTaille;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public List<Fichier> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PlanStockage getPlanStockage() {
        return planStockage;
    }

    public void setPlanStockage(PlanStockage planStockage) {
        this.planStockage = planStockage;
    }
}
