package tn.esprit.ecocycletech.Controller.ReclamationsManagement;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;

@Controller
public class SupportReclamationController {
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.Reclamation;
import tn.esprit.ecocycletech.Entity.ReclamationsManagement.SupportReclamation;
import tn.esprit.ecocycletech.Repository.ReclamationsManagement.ISupportReclamationRepository;
import tn.esprit.ecocycletech.Service.ReclamationsManagement.ISupportReclamationService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/support-reclamations")
@CrossOrigin(origins = "http://localhost:4200")


public class SupportReclamationController {
    @Autowired
    private ISupportReclamationService supportReclamationService;
    @Autowired
    private ISupportReclamationRepository supportReclamationRepository;

    @PostMapping
    public SupportReclamation create(@RequestBody SupportReclamation supportReclamation) {
        return supportReclamationService.addSupportReclamation(supportReclamation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportReclamation> updateSupportReclamation(
            @PathVariable("id") int id,
            @RequestBody SupportReclamation updatedSupportReclamation) {

        Optional<SupportReclamation> existingSupportReclamation = supportReclamationRepository.findById(updatedSupportReclamation.getIdSupportReclamation());

        if (!existingSupportReclamation.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        SupportReclamation supportReclamation = existingSupportReclamation.get();

        // Validate responsible ID, if needed
        if (updatedSupportReclamation.getIdResponsable() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // You can add a more descriptive error message
        }

        // Update basic fields
        supportReclamation.setIdResponsable(updatedSupportReclamation.getIdResponsable());
        supportReclamation.setNomResponsable(updatedSupportReclamation.getNomResponsable());
        supportReclamation.setDateOuvertureSupport(updatedSupportReclamation.getDateOuvertureSupport());
        supportReclamation.setDateClotureSupport(updatedSupportReclamation.getDateClotureSupport());

        // Handle the nested Reclamation object
        if (updatedSupportReclamation.getReclamation() != null) {
            Reclamation updatedReclamation = updatedSupportReclamation.getReclamation();
            Reclamation existingReclamation = supportReclamation.getReclamation();

            // Update properties of Reclamation if needed
            existingReclamation.setTitreReclamation(updatedReclamation.getTitreReclamation());
            existingReclamation.setDescriptionReclamation(updatedReclamation.getDescriptionReclamation());
            existingReclamation.setDateReclamation(updatedReclamation.getDateReclamation());
            existingReclamation.setEtatReclamation(updatedReclamation.getEtatReclamation());

            // Optionally, you can update other fields in the 'reclamation' entity, such as user
            if (updatedReclamation.getUser() != null) {
                existingReclamation.setUser(updatedReclamation.getUser());
            }
        }

        // Save updated SupportReclamation entity
        supportReclamationRepository.save(supportReclamation);

        return ResponseEntity.ok(supportReclamation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        supportReclamationService.deleteSupportReclamation(id);
    }

    @GetMapping("/{id}")
    public SupportReclamation getById(@PathVariable int id) {
        return supportReclamationService.getSupportReclamationById(id);
    }

    @GetMapping
    public List<SupportReclamation> getAll() {
        return supportReclamationService.getAllSupportReclamations();
    }
>>>>>>> e88a1f3 (update)
}
