package tn.esprit.ecocycletech.Controller.EvenementsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.TicketEvenement;
import tn.esprit.ecocycletech.Service.EvenementsManagement.ITicketEvenementService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/tickets")

@RestController
public class TicketEvenementController {
    @Autowired
    ITicketEvenementService ticketEvenementService;
    @PostMapping
    public TicketEvenement saveticket(@RequestBody TicketEvenement ticket) {
        return ticketEvenementService.save(ticket);
    }
    @GetMapping("/{id}")
    public TicketEvenement findById(@PathVariable int id) {
        return ticketEvenementService.findById(id);
    }
    @GetMapping
    public List<TicketEvenement> getAllTicketEvenements() {
        return ticketEvenementService.retrieveAllTicketEvenement();
    }
    @GetMapping("/usertickets/{idUser}")
    public List<TicketEvenement> getTicketsByUser(@PathVariable int idUser) {
        return ticketEvenementService.findByUserId(idUser);
    }
    @PutMapping("/{id}")
    public TicketEvenement updateTicket(@PathVariable int id, @RequestBody TicketEvenement ticket) {
        return ticketEvenementService.update(ticket);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvenement(@PathVariable int id) {
        ticketEvenementService.delete(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}
