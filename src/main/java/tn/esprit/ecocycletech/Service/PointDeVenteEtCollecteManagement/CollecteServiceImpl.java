package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Vehicule;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.IDemandeRecyclageRepository;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.ICollecteRepository;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IPointCollecteRepository;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IVehiculeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CollecteServiceImpl implements ICollecteService {


    private ICollecteRepository CollecteRepository;
    private IVehiculeRepository vehiculeRepository;
    private IDemandeRecyclageRepository demandeRecyclageRepository;
    private  IPointCollecteRepository pointCollecteRepository;


    public CollecteServiceImpl(ICollecteRepository CollecteRepository, IVehiculeRepository vehiculeRepository,
                               IDemandeRecyclageRepository demandeRecyclageRepository, IPointCollecteRepository pointCollecteRepository) {
        this.CollecteRepository = CollecteRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.demandeRecyclageRepository = demandeRecyclageRepository;
        this.pointCollecteRepository = pointCollecteRepository;
    }

    @Override
    public Collecte findById(Integer id) {
        return CollecteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Collecte> findAll() {
        return (List<Collecte>) CollecteRepository.findAll();
    }

    @Override
    public Collecte save(Collecte collecte) {
        CollecteRepository.save(collecte);

        return collecte;
    }

    @Override
    public Collecte update(Collecte collecte) {

        // Check if the collecte object or its ID is null/zero
        if (collecte == null || collecte.getIdCollecte() == 0) {
            throw new IllegalArgumentException("Collecte or its ID cannot be null/zero");
        }

        // Find the existing Collecte by ID
        Optional<Collecte> existingCollecteOptional = CollecteRepository.findById(collecte.getIdCollecte());

        if (existingCollecteOptional.isPresent()) {
            // Get the existing Collecte object
            Collecte existingCollecte = existingCollecteOptional.get();

            // Update the fields of the existing Collecte with the new values
            existingCollecte.setDateCollecte(collecte.getDateCollecte());
            //existingCollecte.setDemandeRecyclage(collecte.getDemandeRecyclage());
            existingCollecte.setPointCollecte(collecte.getPointCollecte());
            existingCollecte.setVehicule(collecte.getVehicule());

            // Save the updated Collecte to the database
            return CollecteRepository.save(existingCollecte);
        } else {
            // Throw an exception if the Collecte is not found
            throw new RuntimeException("Collecte not found with ID: " + collecte.getIdCollecte());
        }
    }

    @Override
    public void delete(Collecte collecte) {
        CollecteRepository.delete(collecte);


    }

    @Override
    public Collecte planCollecte(Integer demandeId, Integer pointCollecteId) {
        // Find the demande
        DemandeRecyclage demande = demandeRecyclageRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande not found"));

        // Find the point of collection
        PointCollecte pointCollecte = pointCollecteRepository.findById(pointCollecteId)
                .orElseThrow(() -> new RuntimeException("Point de collecte not found"));

        LocalDate collecteDate = LocalDate.now().plusDays(1); // tomorrow
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(12, 0);

        // Find available vehicule
        List<Vehicule> availableVehicules = vehiculeRepository.findAvailableVehicule(collecteDate, startTime, endTime);
        if (availableVehicules.isEmpty()) {
            throw new RuntimeException("No available vehicle found for the selected date and time.");
        }
        Vehicule selectedVehicule = availableVehicules.get(0);

        // Create collecte
        Collecte collecte = new Collecte();
        collecte.setDateCollecte(collecteDate);
        collecte.setHeureDebutCollecte(startTime);
        collecte.setHeureFinCollecte(endTime);
        collecte.setScheduled(true);
        collecte.setDemandeRecyclage(demande);
        collecte.setVehicule(selectedVehicule);
        collecte.setPointCollecte(pointCollecte); // This was missing!

        collecte = CollecteRepository.save(collecte);

        return collecte;
    }
}

