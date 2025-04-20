package tn.esprit.ecocycletech.Repository.ReclamationsManagement;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public interface ISupportReclamationRepository {
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.SupportReclamation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ISupportReclamationRepository extends JpaRepository<SupportReclamation, Integer> {

    SupportReclamation findByIdSupportReclamation(int idSupportReclamation);


    List<SupportReclamation> findByNomResponsableContainingIgnoreCase(String nom);

    List<SupportReclamation> findByIdResponsable(int idResponsable);


>>>>>>> e88a1f3 (update)
}
