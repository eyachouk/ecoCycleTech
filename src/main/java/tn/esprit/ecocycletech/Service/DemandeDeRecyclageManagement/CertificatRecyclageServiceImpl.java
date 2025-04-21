package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CertificatRecyclage;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.ICertificatRecyclageRepository;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.IDemandeRecyclageRepository;

@Service
@AllArgsConstructor
public class CertificatRecyclageServiceImpl implements ICertificatRecyclageService {
      @Autowired
      ICertificatRecyclageRepository certificatRecyclageRepository;
@Autowired
IDemandeRecyclageRepository demandeRecyclageRepository;
    @Override
    public CertificatRecyclage addCertificatRecyclage(CertificatRecyclage certificatRecyclage) {
        // Fetch the DemandeRecyclage entity from the database
        DemandeRecyclage demandeRecyclage = demandeRecyclageRepository.findById(certificatRecyclage.getDemandeRecyclage().getIdDemandeRecyclage())
                .orElseThrow(() -> new RuntimeException("DemandeRecyclage not found with id: " + certificatRecyclage.getDemandeRecyclage().getIdDemandeRecyclage()));

        // Set the managed DemandeRecyclage entity to the CertificatRecyclage entity
        certificatRecyclage.setDemandeRecyclage(demandeRecyclage);

        // Save the CertificatRecyclage entity
        return certificatRecyclageRepository.save(certificatRecyclage);
    }

    @Override
    public CertificatRecyclage updateCertificatRecyclage(CertificatRecyclage certificatRecyclage) {
        return certificatRecyclageRepository.save(certificatRecyclage);
    }

    @Override
    public void removeCertificatRecyclage(int idCertificatRecyclage) {
        certificatRecyclageRepository.deleteById(idCertificatRecyclage);
    }

    @Override
    public CertificatRecyclage findById(int idCertificatRecyclage) {
        return certificatRecyclageRepository.findById(idCertificatRecyclage).orElse(null);
    }
}
