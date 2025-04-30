package tn.esprit.ecocycletech.Controller.CommandesManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;
import tn.esprit.ecocycletech.Service.CommandesManagement.ICommandeReparationService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/CommandeReparation")
public class CommandeReparationController {
    private final ICommandeReparationService commandeReparationService;

    public CommandeReparationController(ICommandeReparationService commandeReparationService) {
        this.commandeReparationService = commandeReparationService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCommandeReparationService(@PathVariable int id) {
        try {
            CommandeReparation Reparation = commandeReparationService.findById(id);
            if (Reparation == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Commande de Reparation not found with ID: " + id);
            }
            return ResponseEntity.ok(Reparation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving Commande de Reparation: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCommandeReparationService() {
        try {
            return ResponseEntity.ok(commandeReparationService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving Commande de Reparation: " + e.getMessage());
        }
    }

    @PostMapping("/saveCommandeReparation")
    public ResponseEntity<?> saveCommandeReparationService(@RequestBody CommandeReparation commandeReparation) {
        System.out.println("Received Commande de Reparation: " + commandeReparation);
        try {
            CommandeReparation savedCommandeReparation = commandeReparationService.save(commandeReparation);
            return ResponseEntity.ok(savedCommandeReparation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving Commande de Reparation: " + e.getMessage());
        }
    }

    @PutMapping("/updateCommandeReparation")
    public ResponseEntity<?> updateCommandeReparationService(@RequestBody CommandeReparation commandeReparation) {
        try {
            CommandeReparation updatedCommandeReparation = commandeReparationService.update(commandeReparation);
            return ResponseEntity.ok(updatedCommandeReparation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating Commande de Reparation: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteCommandeReparation/{id}")
    public ResponseEntity<?> deleteCommandeReparationService(@PathVariable int id) {
        try {
            CommandeReparation commandeReparation = commandeReparationService.findById(id);
            if (commandeReparation != null) {
                commandeReparationService.delete(commandeReparation);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Commande de Reparation not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting Commande de Reparation: " + e.getMessage());
        }
    }
}
