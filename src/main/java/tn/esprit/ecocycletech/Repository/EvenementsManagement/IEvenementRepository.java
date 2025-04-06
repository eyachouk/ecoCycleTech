package tn.esprit.ecocycletech.Repository.EvenementsManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;

@Repository
public interface IEvenementRepository extends JpaRepository<Evenement, Integer> {
    Evenement findByIdEvenement(long id);
}
