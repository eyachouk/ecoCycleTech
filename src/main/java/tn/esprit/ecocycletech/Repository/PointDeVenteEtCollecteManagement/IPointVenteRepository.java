package tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointVente;

@Repository
public interface IPointVenteRepository extends JpaRepository<PointVente, Integer> {
}
