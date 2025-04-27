package tn.esprit.ecocycletech.Repository.EvenementsManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;

import java.util.List;

@Repository
public interface IEvenementRepository extends JpaRepository<Evenement, Integer> {
    Evenement findByIdEvenement(long id);
    List<Evenement> findAllByOrderByDateEvenementDesc();
    List<Evenement> findAllByOrderByDateEvenementAsc();
    @Query("SELECT e FROM Evenement e WHERE DATE(e.dateEvenement) = CURRENT_DATE")
    List<Evenement> findEventsForToday();

    @Query("SELECT e FROM Evenement e WHERE DATE(e.dateEvenement) > CURRENT_DATE ORDER BY e.dateEvenement ASC")
    List<Evenement> findUpcomingEvents();

    @Query("SELECT e FROM Evenement e WHERE DATE(e.dateEvenement) < CURRENT_DATE ORDER BY e.dateEvenement DESC")
    List<Evenement> findPastEvents();
}
