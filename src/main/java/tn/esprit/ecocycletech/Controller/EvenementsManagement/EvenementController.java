package tn.esprit.ecocycletech.Controller.EvenementsManagement;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;
import tn.esprit.ecocycletech.Service.EvenementsManagement.IEvenementService;

import java.util.List;

@RequestMapping("/evenement")

@RestController
public class EvenementController {
    @Autowired
    IEvenementService evenementService;
    @PostMapping("/saveevenement")
    public Evenement saveevenement(@RequestBody Evenement event) {
        return evenementService.save(event);
    }
    @GetMapping("/findevenementid/{id}")
    public Evenement findById(@PathVariable int id) {
        return evenementService.findById(id);
    }
    @GetMapping("/allevenemnts")
    public List<Evenement> getAllEvenements() {
        return evenementService.retrieveAllEvenements();
    }
    @PutMapping("/updateevenement/{id}")
    public Evenement updateEvenement(@PathVariable int id,@RequestBody Evenement event) {
        return evenementService.update(event);
    }
    @DeleteMapping("/deleteevenemt/{id}")
    public ResponseEntity<String> deleteEvenement(@PathVariable int id) {
        evenementService.delete(id);
        return ResponseEntity.ok("Event deleted successfully");
    }
}
