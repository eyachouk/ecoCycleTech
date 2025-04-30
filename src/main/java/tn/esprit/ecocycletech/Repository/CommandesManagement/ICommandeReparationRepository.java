package tn.esprit.ecocycletech.Repository.CommandesManagement;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;

import java.util.Optional;

@Repository
public interface ICommandeReparationRepository extends JpaRepository<CommandeReparation, Integer> {
}
