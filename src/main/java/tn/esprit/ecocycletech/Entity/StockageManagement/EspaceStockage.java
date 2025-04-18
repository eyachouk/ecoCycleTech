package tn.esprit.ecocycletech.Entity.StockageManagement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
    @Column(updatable = false)
    private Date dateCreation ;
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;

    @Enumerated(EnumType.STRING)
    private StatutEspace statut;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},mappedBy = "espace")
    @JsonManagedReference
    private List<Fichier> fichiers;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},mappedBy = "espace")
    private User user;
    @ManyToOne
    @JsonBackReference
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

    public StatutEspace getStatut() {
        return statut;
    }

    public void setStatut(StatutEspace statut) {
        this.statut = statut;
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
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }



    @PrePersist
    protected void onCreate() {
        this.dateCreation = new Date(); // set to current date

        // Set expiration = creation + 1 year
      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateCreation);
        calendar.add(Calendar.YEAR, 1); // add 1 year
        this.dateExpiration = calendar.getTime();*/
    }


}



