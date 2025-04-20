package tn.esprit.ecocycletech.Service.ReclamationsManagement;

<<<<<<< HEAD
public interface ISupportReclamationService {
=======
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.SupportReclamation;

import java.util.List;

public interface ISupportReclamationService {
    SupportReclamation addSupportReclamation(SupportReclamation supportReclamation);
    SupportReclamation updateSupportReclamation(SupportReclamation supportReclamation);
    void deleteSupportReclamation(int id);
    SupportReclamation getSupportReclamationById(int id);
    List<SupportReclamation> getAllSupportReclamations();
>>>>>>> e88a1f3 (update)
}
