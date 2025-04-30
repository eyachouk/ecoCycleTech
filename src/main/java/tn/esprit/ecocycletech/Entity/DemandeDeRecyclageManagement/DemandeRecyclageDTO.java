package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import tn.esprit.ecocycletech.Entity.Enumerations.Etat;

import java.util.Date;

public class DemandeRecyclageDTO {
    private Integer idDemandeRecyclage;
    private String title;
    private Date dateCreationDemandeRecyclage;
    private String descriptionDemandeRecyclage;
    private Etat etatDemandeRecyclage;
    private Integer nbrAppareils;
    private Double prixDemandeRecyclage;
    public DemandeRecyclageDTO() {}

    public DemandeRecyclageDTO(Integer idDemandeRecyclage) {
        this.idDemandeRecyclage = idDemandeRecyclage;
    }

    public Integer getIdDemandeRecyclage() {
        return idDemandeRecyclage;
    }

    public void setIdDemandeRecyclage(Integer idDemandeRecyclage) {
        this.idDemandeRecyclage = idDemandeRecyclage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateCreationDemandeRecyclage() {
        return dateCreationDemandeRecyclage;
    }

    public void setDateCreationDemandeRecyclage(Date dateCreationDemandeRecyclage) {
        this.dateCreationDemandeRecyclage = dateCreationDemandeRecyclage;
    }

    public String getDescriptionDemandeRecyclage() {
        return descriptionDemandeRecyclage;
    }

    public void setDescriptionDemandeRecyclage(String descriptionDemandeRecyclage) {
        this.descriptionDemandeRecyclage = descriptionDemandeRecyclage;
    }

    public Etat getEtatDemandeRecyclage() {
        return etatDemandeRecyclage;
    }

    public void setEtatDemandeRecyclage(Etat etatDemandeRecyclage) {
        this.etatDemandeRecyclage = etatDemandeRecyclage;
    }

    public Integer getNbrAppareils() {
        return nbrAppareils;
    }

    public void setNbrAppareils(Integer nbrAppareils) {
        this.nbrAppareils = nbrAppareils;
    }

    public Double getPrixDemandeRecyclage() {
        return prixDemandeRecyclage;
    }

    public void setPrixDemandeRecyclage(Double prixDemandeRecyclage) {
        this.prixDemandeRecyclage = prixDemandeRecyclage;
    }
}
