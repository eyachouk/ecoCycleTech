package tn.esprit.ecocycletech.Entity.StockageManagement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Fichier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFichier;
    private String nom;
    private Long taille;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpload;
    private String urlStockage;
    @ManyToOne
    @JsonBackReference
    private EspaceStockage espace;
    private TypeFichier type;
    private String cloudinaryPublicId;
    private ExtensionFichier extension;


    public long getIdFichier() {
        return idFichier;
    }

    public String getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }

    public void setCloudinaryPublicId(String cloudinaryPublicId) {
        this.cloudinaryPublicId = cloudinaryPublicId;
    }

    public ExtensionFichier getExtension() {
        return extension;
    }

    public void setExtension(ExtensionFichier extension) {
        this.extension = extension;
    }

    public void setIdFichier(long idFichier) {
        this.idFichier = idFichier;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getUrlStockage() {
        return urlStockage;
    }

    public void setUrlStockage(String urlStockage) {
        this.urlStockage = urlStockage;
    }

    public EspaceStockage getEspace() {
        return espace;
    }

    public void setEspace(EspaceStockage espace) {
        this.espace = espace;
    }

    public TypeFichier getType() {
        return type;
    }

    public void setType(TypeFichier type) {
        this.type = type;
    }


    @PrePersist
    protected void onCreate() {
        this.dateUpload = new Date(); // set to current date
    }
}
