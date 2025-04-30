package tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;

@Repository
public interface IDemandeRecyclageRepository extends JpaRepository<DemandeRecyclage, Integer> {
}
