package tn.esprit.ecocycletech.Service.EvenementsManagement;
import java.util.List;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;


public interface ITicketEvenementService {
    public TicketEvenement save(TicketEvenement ticket);
    public TicketEvenement findById(int id);
    public void delete(int id);
    public  TicketEvenement update(TicketEvenement ticket);
    public TicketEvenement add(TicketEvenement ticket);
    List<TicketEvenement> retrieveAllTicketEvenement();
    List<TicketEvenement> findByUserId(int idUser);
}
