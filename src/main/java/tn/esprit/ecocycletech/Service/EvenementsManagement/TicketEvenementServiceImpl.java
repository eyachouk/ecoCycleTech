package tn.esprit.ecocycletech.Service.EvenementsManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
import tn.esprit.ecocycletech.Repository.EvenementsManagement.ITicketEvenementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketEvenementServiceImpl implements ITicketEvenementService {
    @Autowired
    ITicketEvenementRepository ticketEvenementRepository;
    @Override
    public TicketEvenement save(TicketEvenement ticket) {
        return ticketEvenementRepository.save(ticket);
    }

    @Override
    public TicketEvenement findById(int id) {
        return ticketEvenementRepository.findByIdTicketEvenement(id);
    }

    @Override
    public void delete(int id) {
        ticketEvenementRepository.deleteById(id);
    }

    @Override
    public TicketEvenement update(TicketEvenement ticket) {
        TicketEvenement t = ticketEvenementRepository.findByIdTicketEvenement(ticket.getIdTicketEvenement());
        if (t!= null) {
            return ticketEvenementRepository.save(ticket);
        }
        return null;
    }

    @Override
    public TicketEvenement add(TicketEvenement ticket) {
        return ticketEvenementRepository.save(ticket);
    }

    @Override
    public List<TicketEvenement> retrieveAllTicketEvenement() {
        return ticketEvenementRepository.findAll();
    }
}
