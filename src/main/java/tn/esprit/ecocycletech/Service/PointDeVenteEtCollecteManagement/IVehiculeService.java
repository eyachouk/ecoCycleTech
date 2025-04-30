package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Vehicule;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.VehiculeDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IVehiculeService {
    public Vehicule findById(Integer id);
    public List<VehiculeDTO> findAll();
    public Vehicule save(Vehicule vehicule);
    public Vehicule update(Vehicule vehicule);
    public void delete(Vehicule vehicule);
    public List<Vehicule> getAvailableVehicules(LocalDate date, LocalTime startTime, LocalTime endTime);

    }
