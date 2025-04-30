package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Vehicule;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.VehiculeDTO;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.IVehiculeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule findById(Integer id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    @Override
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(v -> new VehiculeDTO(
                        v.getIdVehicule(),
                        v.getMarqueVehicule(),
                        v.getModeleVehicule(),
                        v.getNomChauffeur(),
                        v.getNumTelephoneChauffeur(),
                        v.getCapaciteVehicule()))
                .collect(Collectors.toList());
    }

    @Override
    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        if (vehicule == null || vehicule.getIdVehicule() == 0) {
            throw new IllegalArgumentException("vehicule or its ID cannot be null/zero");
        }
        return vehiculeRepository.findById(vehicule.getIdVehicule())
                .map(existing -> {
                    existing.setMarqueVehicule(vehicule.getMarqueVehicule());
                    existing.setModeleVehicule(vehicule.getModeleVehicule());
                    existing.setNomChauffeur(vehicule.getNomChauffeur());
                    existing.setNumTelephoneChauffeur(vehicule.getNumTelephoneChauffeur());
                    existing.setCapaciteVehicule(vehicule.getCapaciteVehicule());
                    return vehiculeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("vehicule not found with ID: " + vehicule.getIdVehicule()));
    }

    @Override
    public void delete(Vehicule vehicule) {
        if (vehicule != null && vehicule.getIdVehicule() != 0) {
            vehiculeRepository.delete(vehicule);
        } else {
            throw new IllegalArgumentException("Invalid vehicule provided for deletion");
        }
    }
    public List<Vehicule> getAvailableVehicules(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return vehiculeRepository.findAvailableVehicule(date, startTime, endTime);
    }

}
