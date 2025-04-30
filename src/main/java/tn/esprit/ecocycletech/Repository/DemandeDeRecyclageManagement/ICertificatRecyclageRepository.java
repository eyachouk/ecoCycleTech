package tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CertificatRecyclage;

import java.util.Optional;

@Repository
public interface ICertificatRecyclageRepository  extends JpaRepository<CertificatRecyclage, Integer> {
    Optional<CertificatRecyclage> findByDemandeRecyclage_IdDemandeRecyclage(int demandeId);

}
