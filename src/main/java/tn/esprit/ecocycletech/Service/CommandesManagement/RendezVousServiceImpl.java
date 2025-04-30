package tn.esprit.ecocycletech.Service.CommandesManagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;
import tn.esprit.ecocycletech.Repository.CommandesManagement.ICommandeReparationRepository;
import tn.esprit.ecocycletech.Repository.CommandesManagement.IRendezVousRepository;

import java.util.List;

@Service
public class RendezVousServiceImpl implements IRendezVousService {
    private final IRendezVousRepository rendezVousRepository;

    public RendezVousServiceImpl(IRendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }
    @Override
    public RendezVous findById(Integer id) {
        return rendezVousRepository.findById(id).orElse(null);    }

    @Override
    public List<RendezVous> findAll() {
        return (List<RendezVous>) rendezVousRepository.findAll();    }

    @Override
    public RendezVous save(RendezVous rendezVous) {
        rendezVousRepository.save(rendezVous);

        return rendezVous;     }

    @Override
    public RendezVous update(RendezVous rendezVous) {
        if (rendezVous == null || rendezVous.getIdRendezVous() == 0) {
            throw new IllegalArgumentException("RendezVous or its ID cannot be null/zero");
        }
        return rendezVousRepository.findById(rendezVous.getIdRendezVous())
                .map(existing -> {
                    existing.setDateRendezVous(rendezVous.getDateRendezVous());
                    return rendezVousRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("RendezVous not found with ID: " + rendezVous.getIdRendezVous()));
    }

    @Override
    public void delete(RendezVous rendezVous) {
        rendezVousRepository.delete(rendezVous);

    }
}
