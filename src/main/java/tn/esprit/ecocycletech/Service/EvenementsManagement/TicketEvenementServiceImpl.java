package tn.esprit.ecocycletech.Service.EvenementsManagement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
import tn.esprit.ecocycletech.Repository.EvenementsManagement.IEvenementRepository;
import tn.esprit.ecocycletech.Repository.EvenementsManagement.ITicketEvenementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketEvenementServiceImpl implements ITicketEvenementService {
    @Autowired
    ITicketEvenementRepository ticketEvenementRepository;
    @Autowired
    IEvenementRepository evenementRepository ;
    @Override
    public TicketEvenement save(TicketEvenement ticket) {
        // Step 1: Fetch the complete Evenement object from DB using the ID
        int eventId = ticket.getEvenement().getIdEvenement();
        Evenement event = evenementRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
        if (event.getNbrPlacesEvenement() <= 0) {
            throw new RuntimeException("No more places available for this event.");
        }
        // Step 2: Decrement the number of available places
        int newCapacity = event.getNbrPlacesEvenement() - 1;
        event.setNbrPlacesEvenement(newCapacity);

        // Step 3: Assign the full event back to the ticket
        ticket.setEvenement(event);

        // Step 4: Save both updated event and the new ticket
        evenementRepository.save(event);
        return ticketEvenementRepository.save(ticket);
    }

    @Override
    public TicketEvenement findById(int id) {
        return ticketEvenementRepository.findByIdTicketEvenement(id);
    }

    @Override
    public void delete(int id) {
        TicketEvenement ticket = ticketEvenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
        // Step 1: Get the full Evenement from DB
        Evenement event = evenementRepository.findById(ticket.getEvenement().getIdEvenement())
                .orElseThrow(() -> new RuntimeException("Event not found for the ticket"));
        // Step 2: Increment the number of places
        event.setNbrPlacesEvenement(event.getNbrPlacesEvenement() + 1);
        evenementRepository.save(event);
        ticketEvenementRepository.deleteById(id);}

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

    @Override
    public List<TicketEvenement> findByUserId(int idUser) {
        return ticketEvenementRepository.findByUserIdUser(idUser);
    }
}
