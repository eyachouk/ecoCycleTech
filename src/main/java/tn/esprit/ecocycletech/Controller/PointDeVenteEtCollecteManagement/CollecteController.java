package tn.esprit.ecocycletech.Controller.PointDeVenteEtCollecteManagement;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;
import tn.esprit.ecocycletech.Repository.PointDeVenteEtCollecteManagement.ICollecteRepository;
import tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement.ICollecteService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Collect")
public class CollecteController {
    ICollecteService collecteService;
    @PostMapping("/saveCollect")
    public Collecte saveCollect(@RequestBody Collecte collecte) {
        return collecteService.save(collecte);
    }
    @GetMapping("/getCollect/{id}")
    public Collecte getCollecte(@PathVariable int id) {
        return collecteService.findById(id);
    }
    @GetMapping("/all")
    public List<Collecte> getAllCollect() {
        return collecteService.findAll();
    }
    @PutMapping("/updateCollect")
    public Collecte updateCollect(@RequestBody Collecte collecte) {
        return collecteService.update(collecte);
    }

    @DeleteMapping("/deleteCollect/{id}")
    public void deleteCollect(@PathVariable int id) {
        Collecte collecte = collecteService.findById(id);
        if (collecte != null) {
            collecteService.delete(collecte);
        }
    }
    @PostMapping("/planCollecte/{idDemande}/{idPoint}")
    public ResponseEntity<Map<String, String>> planCollecte(
            @PathVariable int idDemande,
            @PathVariable int idPoint) {

        try {
            // Your existing business logic
            collecteService.planCollecte(idDemande, idPoint);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "status", "success",
                            "message", "Pickup scheduled successfully"
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }

}
