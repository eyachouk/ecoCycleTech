package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;

import java.util.List;

public interface IDemandeRecyclageService {
    List<DemandeRecyclage> findAll();
    DemandeRecyclage addDemandeRecyclage(DemandeRecyclage demandeRecyclage);
    DemandeRecyclage updateDemandeRecyclage(DemandeRecyclage demandeRecyclage);
    void removeDemandeRecyclage(int idDemandeRecyclage);
    DemandeRecyclage findById(int idDemandeRecyclage);
}
