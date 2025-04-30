package tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Entity.Enumerations.Disponibilite;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface IPointCollecteRepository extends JpaRepository<PointCollecte, Integer> {
    @Query("SELECT cp FROM PointCollecte cp WHERE " +
            "(cp.disponibilitePointCollecte = tn.esprit.ecocycletech.Entity.Enumerations.Disponibilite.DISPONIBLE OR " +
            "(cp.disponibilitePointCollecte = tn.esprit.ecocycletech.Entity.Enumerations.Disponibilite.SATUREE AND cp.capacitePointCollecte > 0)) AND " +
            "cp.heureOuverturePointCollecte <= :currentTime AND " +
            "cp.heureFermeturePointCollecte >= :currentTime AND " +
            ":currentDay MEMBER OF cp.availableDays")
    List<PointCollecte> findAvailableCollectionPoints(String currentDay, LocalTime currentTime);
}

