package tn.esprit.ecocycletech.Entity.ReclamationsManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
<<<<<<< HEAD
@Setter
@Getter
=======
>>>>>>> e88a1f3 (update)
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
<<<<<<< HEAD
=======

    public int getIdSupportReclamation() {
        return idSupportReclamation;
    }

    public void setIdSupportReclamation(int idSupportReclamation) {
        this.idSupportReclamation = idSupportReclamation;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public Date getDateOuvertureSupport() {
        return dateOuvertureSupport;
    }

    public void setDateOuvertureSupport(Date dateOuvertureSupport) {
        this.dateOuvertureSupport = dateOuvertureSupport;
    }

    public Date getDateClotureSupport() {
        return dateClotureSupport;
    }

    public void setDateClotureSupport(Date dateClotureSupport) {
        this.dateClotureSupport = dateClotureSupport;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

>>>>>>> e88a1f3 (update)
}
