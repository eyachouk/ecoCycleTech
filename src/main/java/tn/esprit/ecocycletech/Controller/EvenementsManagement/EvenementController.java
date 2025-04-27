package tn.esprit.ecocycletech.Controller.EvenementsManagement;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
import tn.esprit.ecocycletech.Service.EvenementsManagement.IEvenementService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/evenement")

@RestController
public class EvenementController {
    @Autowired
    IEvenementService evenementService;
    @PostMapping
    public Evenement saveevenement(@RequestBody Evenement event) {
        return evenementService.save(event);
    }
    @GetMapping("/{id}")
    public Evenement findById(@PathVariable int id) {
        return evenementService.findById(id);
    }
    @GetMapping
    public List<Evenement> getAllEvenements() {
        return evenementService.retrieveAllEvenements();
    }
    @PutMapping("/{id}")
    public Evenement updateEvenement(@PathVariable int id,@RequestBody Evenement event) {
        return evenementService.update(event);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvenement(@PathVariable int id) {
        evenementService.delete(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
    @GetMapping("/recent")
    public List<Evenement> getEventsSortedByDateDesc() {
        return evenementService.getEventsSortedByDateDesc();
    }
    @GetMapping("/oldest")
    public List<Evenement> getEventsSortedByDateAsc() {
        return evenementService.getEventsSortedByDateAsc();
    }
    @GetMapping("/today")
    public List<Evenement> getTodayEvents() {
        return evenementService.getTodayEvents();
    }

    @GetMapping("/upcoming")
    public List<Evenement> getUpcomingEvents() {
        return evenementService.getUpcomingEvents();
    }

    @GetMapping("/past")
    public List<Evenement> getPastEvents() {
        return evenementService.getPastEvents();
    }
}
