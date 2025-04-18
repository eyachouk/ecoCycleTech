package tn.esprit.ecocycletech.Repository.AppareilsManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;

public interface IFactureRepository extends JpaRepository<Facture, Integer> {
}
