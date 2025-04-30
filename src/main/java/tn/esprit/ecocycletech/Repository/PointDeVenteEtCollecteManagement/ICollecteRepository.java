package tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;

import java.util.List;

@Repository
public interface ICollecteRepository extends JpaRepository<Collecte, Integer> {
    @EntityGraph(attributePaths = {"demandeRecyclage", "vehicule", "pointCollecte"})
    List<Collecte> findAll();

}
