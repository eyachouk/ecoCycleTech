package tn.esprit.ecocycletech.Entity.StockageManagement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private long idFichier;
    private String nom;
    private BigDecimal taille;
    @Temporal(TemporalType.DATE)
    private Date dateUpload;
    private String urlStockage;
    @ManyToOne
    private EspaceStockage espace;
    private TypeFichier type;

    public long getIdFichier() {
        return idFichier;
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

    public BigDecimal getTaille() {
        return taille;
    }

    public void setTaille(BigDecimal taille) {
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
}
