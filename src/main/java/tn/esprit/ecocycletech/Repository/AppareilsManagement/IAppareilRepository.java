package tn.esprit.ecocycletech.Repository.AppareilsManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppareilRepository extends JpaRepository<Appareil, Integer> {}

