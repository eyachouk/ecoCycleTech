package tn.esprit.ecocycletech.Service.ReclamationsManagement;

<<<<<<< HEAD
public interface IReclamationService {
=======
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IReclamationService  {
    boolean existsByTitreReclamation(String titreReclamation);
    Reclamation addReclamation(Reclamation reclamation);
    Reclamation updateReclamation(Reclamation reclamation);
    void deleteReclamation(int idReclamation);
    List<Reclamation> getAllReclamations();
    Reclamation getReclamationById(int idReclamation);
    Optional<Reclamation> getReclamationsByUserId(int userId);
    List<Reclamation> getReclamationsByEtat(Etat etat);
    List<Reclamation> searchReclamationsByTitre(String keyword);
    List<Reclamation> getReclamationsByDate(Date date);
>>>>>>> e88a1f3 (update)
}
