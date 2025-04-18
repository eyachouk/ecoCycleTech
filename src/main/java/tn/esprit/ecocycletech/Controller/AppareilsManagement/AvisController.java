package tn.esprit.ecocycletech.Controller.AppareilsManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
import tn.esprit.ecocycletech.Service.AppareilsManagement.IAvisService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avis")
@CrossOrigin(origins = "http://localhost:4200")
public class AvisController {
    @Autowired
    private IAvisService avisService;
    @GetMapping("/average/{idAppareil}")
    public ResponseEntity<Double> getAverageRating(@PathVariable int idAppareil) {
        try {
            Double avg = avisService.getAverageRating(idAppareil);
            return ResponseEntity.ok(avg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<Avis> addAvis(@RequestBody Avis avis) {
        try {
            Avis createdAvis = avisService.saveAvis(avis);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAvis);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<Avis> getAllAvis() {
        return avisService.getAllAvis();
    }

    @GetMapping("/{id}")
    public Optional<Optional<Avis>> getAvisById(@PathVariable int id) {
        return Optional.ofNullable(avisService.getAvisById(id));
    }

    @PutMapping("/{id}")
    public Avis updateAvis(@PathVariable int id, @RequestBody Avis updatedAvis) {
        return avisService.updateAvis(id, updatedAvis);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAvis(@PathVariable int id) {
        return avisService.deleteAvis(id);
    }
}
