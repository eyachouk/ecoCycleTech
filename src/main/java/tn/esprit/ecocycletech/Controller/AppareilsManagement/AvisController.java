package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IAppareilRepository;
import tn.esprit.ecocycletech.Repository.UserManagement.IUserRepository;
import tn.esprit.ecocycletech.Service.AppareilsManagement.IAvisService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/avis")
@CrossOrigin(origins = "http://localhost:4200")
public class AvisController {

    @Autowired
    private IAvisService avisService;

    @Autowired
    private IAppareilRepository appareilRepo;

    @Autowired
    private IUserRepository userRepo;

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
    public ResponseEntity<Avis> updateAvis(
            @PathVariable int id,
            @RequestBody JsonNode payload
    ) {
        // 1) unwrap Optional<Avis>
        Avis existing = avisService.getAvisById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Avis non trouvé: " + id
                ));

        // 2) contenu
        if (payload.has("contenu")) {
            existing.setContenu(payload.get("contenu").asText());
        }

        // 3) rating
        if (payload.has("rating")) {
            int r = payload.get("rating").asInt();
            if (r >= 1 && r <= 5) {
                existing.setRating(r);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "rating must be between 1 and 5"
                );
            }
        }

        // 4) appareil
        if (payload.has("appareil") && payload.get("appareil").has("idAppareil")) {
            int appId = payload.get("appareil").get("idAppareil").asInt();
            Appareil app = appareilRepo.findById(appId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Appareil introuvable: " + appId
                    ));
            existing.setAppareil(app);
        }

        // 5) user
        if (payload.has("user") && payload.get("user").has("idUser")) {
            int userId = payload.get("user").get("idUser").asInt();
            User u = userRepo.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Utilisateur introuvable: " + userId
                    ));
            existing.setUser(u);
        }

        Avis saved = avisService.updateAvis(id, existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAvis(@PathVariable int id) {
        return avisService.deleteAvis(id);
    }
}
