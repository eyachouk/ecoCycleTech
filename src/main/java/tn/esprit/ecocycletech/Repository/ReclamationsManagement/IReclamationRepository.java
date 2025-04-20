package tn.esprit.ecocycletech.Repository.ReclamationsManagement;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public interface IReclamationRepository {
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReclamationRepository extends JpaRepository<Reclamation, Integer> {

    Reclamation findByIdReclamation(int idReclamation);

    Optional<Reclamation> findByUserIdUser(int idUser); // ✅

    List<Reclamation> findByEtatReclamation(Etat etat);

    List<Reclamation> findByTitreReclamationContainingIgnoreCase(String keyword);

    List<Reclamation> findByDateReclamation(Date date);

    boolean existsByTitreReclamation(String titreReclamation);
>>>>>>> e88a1f3 (update)
}
