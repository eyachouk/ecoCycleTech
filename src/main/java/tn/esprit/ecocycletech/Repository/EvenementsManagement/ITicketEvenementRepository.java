package tn.esprit.ecocycletech.Repository.EvenementsManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;

import java.util.List;

@Repository
public interface ITicketEvenementRepository extends JpaRepository<TicketEvenement, Integer>
{
    TicketEvenement findByIdTicketEvenement(int id);
    List <TicketEvenement> findByUserIdUser(int idUser);
}
