package tn.esprit.ecocycletech.Repository.CommandesManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;

@Repository
public interface IRendezVousRepository extends JpaRepository<RendezVous, Integer> {
}
