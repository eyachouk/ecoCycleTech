package tn.esprit.ecocycletech.Controller.CommandesManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;
import tn.esprit.ecocycletech.Service.CommandesManagement.IRendezVousService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/RendezVousReparation")
public class RendezVousController {

    private final IRendezVousService RendezVousService;

    public RendezVousController(IRendezVousService RendezVousService) {
        this.RendezVousService = RendezVousService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRendezVousReparationService(@PathVariable int id) {
        try {
            RendezVous RendezVous = RendezVousService.findById(id);
            if (RendezVous == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("RendezVous de Reparation not found with ID: " + id);
            }
            return ResponseEntity.ok(RendezVous);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving RendezVous de Reparation: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getRendezVousReparationService() {
        try {
            return ResponseEntity.ok(RendezVousService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving RendezVous de Reparation: " + e.getMessage());
        }
    }

    @PostMapping("/saveRendezVousReparation")
    public ResponseEntity<?> saveRendezVousReparationService(@RequestBody RendezVous rendezVous) {
        System.out.println("Received RendezVous de Reparation: " + rendezVous);
        try {
            RendezVous savedRendezVous = RendezVousService.save(rendezVous);
            return ResponseEntity.ok(savedRendezVous);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving RendezVous de Reparation: " + e.getMessage());
        }
    }

    @PutMapping("/updateRendezVousReparation")
    public ResponseEntity<?> updateRendezVousReparationService(@RequestBody RendezVous rendezVous) {
        try {
            RendezVous updatedRendezVous = RendezVousService.update(rendezVous);
            return ResponseEntity.ok(updatedRendezVous);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating RendezVous de Reparation: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteRendezVousReparation/{id}")
    public ResponseEntity<?> deleteRendezVousReparationService(@PathVariable int id) {
        try {
            RendezVous rendezVous = RendezVousService.findById(id);
            if (rendezVous != null) {
                RendezVousService.delete(rendezVous);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("RendezVous de Reparation not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting RendezVous de Reparation: " + e.getMessage());
        }
    }
}
