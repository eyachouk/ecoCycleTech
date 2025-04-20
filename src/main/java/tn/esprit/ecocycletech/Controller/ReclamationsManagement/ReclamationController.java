package tn.esprit.ecocycletech.Controller.ReclamationsManagement;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;

@Controller
public class ReclamationController {
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Service.ReclamationsManagement.IReclamationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reclamations")
public class ReclamationController {

    private final IReclamationService reclamationService;
     

    // Injection de dépendance via le constructeur
    @Autowired
    public ReclamationController(IReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @PostMapping
    public Reclamation addReclamation(@RequestBody Reclamation reclamation) {


        return this.reclamationService.addReclamation(reclamation);
    }

    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable int id, @RequestBody Reclamation reclamation) {
        reclamation.setIdReclamation(id);
        return this.reclamationService.updateReclamation(reclamation);
    }
    
    

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable int id) {
        reclamationService.deleteReclamation(id);
    }

    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return this.reclamationService.getAllReclamations();
    }

    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable int id) {
        return this.reclamationService.getReclamationById(id);
    }

   @GetMapping("/user/{userId}")
    public Optional<Reclamation> getReclamationsByUserId(@PathVariable int userId) {
        return reclamationService.getReclamationsByUserId(userId);
    }

    @GetMapping("/etat/{etat}")
    public List<Reclamation> getReclamationsByEtat(@PathVariable String etat) {
        return reclamationService.getReclamationsByEtat(tn.esprit.ecocycletech.Entity.Enumerations.Etat.valueOf(etat));
    }

    @GetMapping("/search")
    public List<Reclamation> searchReclamationsByTitre(@RequestParam String keyword) {
        return reclamationService.searchReclamationsByTitre(keyword);
    }
   /* @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable int id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        if (reclamation == null) {
            return ResponseEntity.notFound().build();  // Return 404 if not found
        }
        return ResponseEntity.ok(reclamation);  // Return 200 with the reclamation object if found
    }*/

 

>>>>>>> e88a1f3 (update)
}
