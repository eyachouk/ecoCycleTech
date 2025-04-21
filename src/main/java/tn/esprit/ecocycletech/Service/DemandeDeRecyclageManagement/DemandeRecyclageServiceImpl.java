package tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.IDemandeRecyclageRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DemandeRecyclageServiceImpl implements IDemandeRecyclageService {
    @Autowired
    IDemandeRecyclageRepository demandeRecyclageRepository;

    @Override
    public List<DemandeRecyclage> findAll() {
        return demandeRecyclageRepository.findAll();
    }

    @Override
    public DemandeRecyclage addDemandeRecyclage(DemandeRecyclage demandeRecyclage) {
        return demandeRecyclageRepository.save(demandeRecyclage);
    }

    @Override
    public DemandeRecyclage updateDemandeRecyclage(DemandeRecyclage demandeRecyclage) {
        return demandeRecyclageRepository.save(demandeRecyclage);
    }

    @Override
    public void removeDemandeRecyclage(int idDemandeRecyclage) {
        if (demandeRecyclageRepository.existsById(idDemandeRecyclage)) {
            demandeRecyclageRepository.deleteById(idDemandeRecyclage);
        } else {
            throw new RuntimeException("DemandeRecyclage with ID " + idDemandeRecyclage + " not found.");
        }
    }

    @Override
    public DemandeRecyclage findById(int idDemandeRecyclage) {
        return demandeRecyclageRepository.findById(idDemandeRecyclage)
                .orElseThrow(() -> new RuntimeException("DemandeRecyclage with ID " + idDemandeRecyclage + " not found."));
    }
}
