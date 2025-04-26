package tn.esprit.ecocycletech.Repository.AppareilsManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

}
