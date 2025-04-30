package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CertificatRecyclage;

public interface ICertificatRecyclageService {
    CertificatRecyclage addCertificatRecyclage(CertificatRecyclage certificatRecyclage);
    CertificatRecyclage updateCertificatRecyclage(CertificatRecyclage certificatRecyclage);
    void removeCertificatRecyclage(int idCertificatRecyclage);
    CertificatRecyclage findById(int idCertificatRecyclage);
    CertificatRecyclage findByDemandeId(int demandeId);

}
