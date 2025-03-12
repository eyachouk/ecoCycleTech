package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Service.AppareilsManagement.IAppareilService;

import java.util.List;

@RestController
@RequestMapping("/api/appareils")
@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes CORS venant d'Angular
public class AppareilController {

    @Autowired
    private IAppareilService appareilService;

    @GetMapping("AllAppareils")
    public List<Appareil> getAllAppareils() {
        return appareilService.getAllAppareils();
    }

    @GetMapping("/getAppareil/{id}")
    public ResponseEntity<Appareil> getAppareilById(@PathVariable int id) {
        Appareil appareil = appareilService.getAppareilById(id);
        if (appareil != null) {
            return ResponseEntity.ok(appareil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("createAppareil")
    public ResponseEntity<Appareil> addAppareil(@RequestBody Appareil appareil) {
        return ResponseEntity.ok(appareilService.saveAppareil(appareil));
    }

    @PutMapping("UpdateAppareil/{id}")
    public ResponseEntity<Appareil> updateAppareil(@PathVariable int id, @RequestBody Appareil appareil) {
        Appareil updatedAppareil = appareilService.updateAppareil(id, appareil);

        if (updatedAppareil != null) {
            return ResponseEntity.ok(updatedAppareil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un appareil
    @DeleteMapping("deleteappareil/{id}")
    public ResponseEntity<Void> deleteAppareil(@PathVariable int id) {
        if (appareilService.deleteAppareil(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
