package tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertificatRecyclage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCertificatRecyclage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEmissionCertificat;
    @OneToOne
    @JoinColumn(name = "demandeRecyclageCertificat", referencedColumnName = "idDemandeRecyclage")
    @JsonIgnore
    private DemandeRecyclage demandeRecyclage;

    public DemandeRecyclage getDemandeRecyclage() {
        return demandeRecyclage;
    }

    public void setDemandeRecyclage(DemandeRecyclage demandeRecyclage) {
        this.demandeRecyclage = demandeRecyclage;
    }

    public Date getDateEmissionCertificat() {
        return dateEmissionCertificat;
    }

    @Override
    public String toString() {
        return "CertificatRecyclage{" +
                "idCertificatRecyclage=" + idCertificatRecyclage +
                ", dateEmissionCertificat=" + dateEmissionCertificat +
                ", demandeRecyclage=" + demandeRecyclage +
                '}';
    }

    public void setDateEmissionCertificat(Date dateEmissionCertificat) {
        this.dateEmissionCertificat = dateEmissionCertificat;
    }

    public int getIdCertificatRecyclage() {
        return idCertificatRecyclage;
    }

    public void setIdCertificatRecyclage(int idCertificatRecyclage) {
        this.idCertificatRecyclage = idCertificatRecyclage;
    }
}
