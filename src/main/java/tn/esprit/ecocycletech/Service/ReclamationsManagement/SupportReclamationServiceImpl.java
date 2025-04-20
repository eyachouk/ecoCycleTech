package tn.esprit.ecocycletech.Service.ReclamationsManagement;

import lombok.AllArgsConstructor;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupportReclamationServiceImpl {
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.SupportReclamation;
import tn.esprit.ecocycletech.Repository.ReclamationsManagement.IReclamationRepository;
import tn.esprit.ecocycletech.Repository.ReclamationsManagement.ISupportReclamationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SupportReclamationServiceImpl implements ISupportReclamationService {


  @Autowired
    private ISupportReclamationRepository supportReclamationRepository;
  @Autowired
    private IReclamationRepository reclamationRepository ;



    @Override
    public SupportReclamation addSupportReclamation(SupportReclamation supportReclamation) {
        Reclamation reclamation = supportReclamation.getReclamation();

        if (reclamation != null ) {
            // Si la réclamation n'existe pas encore → on la sauvegarde
            if (!reclamationRepository.existsById(reclamation.getIdReclamation())) {
                reclamation = reclamationRepository.save(reclamation); // sauvegarde nouvelle réclamation
            } else {
                // Sinon, on récupère l'existante
                reclamation = reclamationRepository.findById(reclamation.getIdReclamation()).get();
            }
            supportReclamation.setReclamation(reclamation); // lier la réclamation
        }

        return supportReclamationRepository.save(supportReclamation);
    }


    @Override
    public SupportReclamation updateSupportReclamation(SupportReclamation supportReclamation) {
        Optional<SupportReclamation> existingSupportOpt = supportReclamationRepository.findById(supportReclamation.getIdSupportReclamation());

        if (existingSupportOpt.isPresent()) {
            SupportReclamation existingSupport = existingSupportOpt.get();

            existingSupport.setIdResponsable(supportReclamation.getIdResponsable());
            existingSupport.setNomResponsable(supportReclamation.getNomResponsable());
            existingSupport.setDateOuvertureSupport(supportReclamation.getDateOuvertureSupport());
            existingSupport.setDateClotureSupport(supportReclamation.getDateClotureSupport());

            // Handle Reclamation association if provided
            Reclamation updatedReclamation = supportReclamation.getReclamation();
            if (updatedReclamation != null && updatedReclamation.getIdReclamation() != 0) {
                Optional<Reclamation> existingReclamation = reclamationRepository.findById(updatedReclamation.getIdReclamation());
                existingReclamation.ifPresent(existingSupport::setReclamation);
            }

            return supportReclamationRepository.save(existingSupport);
        } else {
            throw new RuntimeException("SupportReclamation with ID " + supportReclamation.getIdSupportReclamation() + " not found.");
        }
    }


    @Override
    public void deleteSupportReclamation(int id) {
        supportReclamationRepository.deleteById(id);
    }

    @Override
    public SupportReclamation getSupportReclamationById(int id) {
        return supportReclamationRepository.findById(id).orElse(null);
    }

    @Override
    public List<SupportReclamation> getAllSupportReclamations() {
        return supportReclamationRepository.findAll();
    }

>>>>>>> e88a1f3 (update)
}
