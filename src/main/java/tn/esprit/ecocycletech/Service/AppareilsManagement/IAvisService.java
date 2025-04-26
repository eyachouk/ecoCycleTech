package tn.esprit.ecocycletech.Service.AppareilsManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;

import java.util.List;
import java.util.Optional;

public interface IAvisService {
    List<Avis> getAllAvis();
    Optional<Avis> getAvisById(int id);
     Avis saveAvis(Avis avis);
    boolean deleteAvis(int id);
    Avis updateAvis(int id, Avis avis);
    public Double getAverageRating(int idAppareil);
}
