package tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Vehicule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IVehiculeRepository  extends JpaRepository<Vehicule, Integer> {
    @Query("SELECT v FROM Vehicule v WHERE v.idVehicule NOT IN (" +
            "SELECT c.vehicule.idVehicule FROM Collecte c " +
            "WHERE c.dateCollecte = :dateCollecte " +
            "AND ((:startTime BETWEEN c.heureDebutCollecte AND c.heureFinCollecte) " +
            "     OR (:endTime BETWEEN c.heureDebutCollecte AND c.heureFinCollecte) " +
            "     OR (c.heureDebutCollecte BETWEEN :startTime AND :endTime) " +
            "     OR (c.heureFinCollecte BETWEEN :startTime AND :endTime))" +
            ")")
    List<Vehicule> findAvailableVehicule(
            @Param("dateCollecte") LocalDate dateCollecte,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );


}

