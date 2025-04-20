package tn.esprit.ecocycletech.Entity.ReclamationsManagement;

<<<<<<< HEAD
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
=======
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.UserManagement.User;

@Entity
>>>>>>> e88a1f3 (update)
@AllArgsConstructor
@NoArgsConstructor
public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReclamation;
    private String titreReclamation;
    private String descriptionReclamation;
    @Temporal(TemporalType.DATE)
<<<<<<< HEAD
    private Date dateReclamation;
=======
@Column(updatable = false, nullable = false)   
 private Date dateReclamation;
    @Enumerated()
>>>>>>> e88a1f3 (update)
    private Etat etatReclamation;
    @OneToOne(mappedBy = "reclamation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SupportReclamation supportReclamation;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
<<<<<<< HEAD
=======
    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getTitreReclamation() {
        return titreReclamation;
    }

    public void setTitreReclamation(String titreReclamation) {
        this.titreReclamation = titreReclamation;
    }

    public String getDescriptionReclamation() {
        return descriptionReclamation;
    }

    public void setDescriptionReclamation(String descriptionReclamation) {
        this.descriptionReclamation = descriptionReclamation;
    }

    public Date getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(Date dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Etat getEtatReclamation() {
        return etatReclamation;
    }

    public void setEtatReclamation(Etat etatReclamation) {
        this.etatReclamation = etatReclamation;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
>>>>>>> e88a1f3 (update)

}
