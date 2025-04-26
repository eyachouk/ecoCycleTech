package tn.esprit.ecocycletech.Repository.AppareilsManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;

import java.util.List;

public interface IAvisRepository extends JpaRepository<Avis, Integer> {
    @Query("SELECT AVG(a.rating) FROM Avis a WHERE a.appareil.idAppareil = :idAppareil")
    Double findAverageRatingByAppareil(@Param("idAppareil") int idAppareil);

}
