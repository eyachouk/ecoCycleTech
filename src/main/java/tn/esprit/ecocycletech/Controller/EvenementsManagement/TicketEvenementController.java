package tn.esprit.ecocycletech.Controller.EvenementsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
import tn.esprit.ecocycletech.Service.EvenementsManagement.ITicketEvenementService;

import java.util.List;

@RequestMapping("/tickets")


@RestController
public class TicketEvenementController {
    @Autowired
    ITicketEvenementService ticketEvenementService;
    @PostMapping("/saveticket")
    public TicketEvenement saveticket(@RequestBody TicketEvenement ticket) {
        return ticketEvenementService.save(ticket);
    }
    @GetMapping("/findticketid/{id}")
    public TicketEvenement findById(@PathVariable int id) {
        return ticketEvenementService.findById(id);
    }
    @GetMapping("/allevenemnts")
    public List<TicketEvenement> getAllTicketEvenements() {
        return ticketEvenementService.retrieveAllTicketEvenement();
    }
    @PutMapping("/updateticket")
    public TicketEvenement updateTicket(@PathVariable int id, @RequestBody TicketEvenement ticket) {
        return ticketEvenementService.update(ticket);
    }
    @DeleteMapping("/deleteticket/{id}")
    public ResponseEntity<String> deleteEvenement(@PathVariable int id) {
        ticketEvenementService.delete(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}
