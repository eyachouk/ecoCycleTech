package tn.esprit.ecocycletech.Service.CommandesManagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Repository.CommandesManagement.ICommandeReparationRepository;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IPointCollecteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeReparationServiceImpl implements ICommandeReparationService {
    private final ICommandeReparationRepository commandeReparationRepository;

    public CommandeReparationServiceImpl(ICommandeReparationRepository commandeReparationRepository) {
        this.commandeReparationRepository = commandeReparationRepository;
    }

    @Override
    public CommandeReparation findById(Integer id) {
        return commandeReparationRepository.findById(id).orElse(null);    }

    @Override
    public List<CommandeReparation> findAll() {
        return (List<CommandeReparation>) commandeReparationRepository.findAll();    }

    @Override
    public CommandeReparation save(CommandeReparation CommandeReparation) {
        commandeReparationRepository.save(CommandeReparation);

        return CommandeReparation;    }

    @Override
    public CommandeReparation update(CommandeReparation CommandeReparation) {
        if (CommandeReparation == null || CommandeReparation.getIdCommandeReparation() == 0) {
            throw new IllegalArgumentException("CommandeReparation or its ID cannot be null/zero");
        }
        return commandeReparationRepository.findById(CommandeReparation.getIdCommandeReparation())
                .map(existing -> {
                    existing.setTitreCommande(CommandeReparation.getTitreCommande());
                    existing.setTypeAppareil(CommandeReparation.getTypeAppareil());
                    existing.setDescriptionCommande(CommandeReparation.getDescriptionCommande());
                    existing.setTypeCollecteCommande(CommandeReparation.getTypeCollecteCommande());
                    existing.setDateCreationCommande(CommandeReparation.getDateCreationCommande());
                    return commandeReparationRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("PointCollecte not found with ID: " + CommandeReparation.getIdCommandeReparation()));
    }

    @Override
    public void delete(CommandeReparation CommandeReparation) {
        commandeReparationRepository.delete(CommandeReparation);
    }
}
