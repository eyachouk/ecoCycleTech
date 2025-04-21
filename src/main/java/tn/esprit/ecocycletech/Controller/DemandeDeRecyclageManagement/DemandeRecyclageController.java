    package tn.esprit.ecocycletech.Controller.DemandeDeRecyclageManagement;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.AllArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
    import tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement.IDemandeRecyclageService;

    import java.io.IOException;
    import java.util.Base64;
    import java.util.List;
    @CrossOrigin(origins = "http://localhost:4200")
    @Validated
    @RestController
    @RequestMapping("/demandeRecyclage")
    @AllArgsConstructor
    public class DemandeRecyclageController {
        @Autowired
        IDemandeRecyclageService demandeRecyclageService;
        // Save a new DemandeRecyclage
        @PostMapping(value = "/save", consumes = {"multipart/form-data"})
        public DemandeRecyclage saveDemande(
                @RequestPart("demande") String demandeJson,
                @RequestPart(value = "file", required = false) MultipartFile file
        ) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            DemandeRecyclage demande = mapper.readValue(demandeJson, DemandeRecyclage.class);

            if (file != null && !file.isEmpty()) {
                try {
                    demande.setImageData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors du traitement de l'image", e);
                }
            }

            return demandeRecyclageService.addDemandeRecyclage(demande);
        }




        // Get all DemandeRecyclage
        @GetMapping("/all")
        public List<DemandeRecyclage> getAllDemandes() {
            List<DemandeRecyclage> demandes = demandeRecyclageService.findAll();
            for (DemandeRecyclage demande : demandes) {
                if (demande.getImageData() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(demande.getImageData());
                }
            }
            return demandes;
        }
        // Get a specific DemandeRecyclage by ID
        @GetMapping("/{id}")
        public ResponseEntity<DemandeRecyclage> getDemandeById(@PathVariable int id) {
            DemandeRecyclage demandeRecyclage = demandeRecyclageService.findById(id);
            if (demandeRecyclage != null && demandeRecyclage.getImageData() != null) {
                // Convert imageData to Base64 string for easier display in frontend
                String base64Image = Base64.getEncoder().encodeToString(demandeRecyclage.getImageData());
                demandeRecyclage.setImageData(base64Image.getBytes());  // Update the image data as Base64
            }
            return ResponseEntity.ok(demandeRecyclage);
        }

        // Update an existing DemandeRecyclage
        @CrossOrigin(origins = "http://localhost:4200")
        @PutMapping(value = "/update", consumes = {"multipart/form-data"})
        public DemandeRecyclage updateDemande(
                @RequestPart("demande") String demandeJson,
                @RequestPart(value = "file", required = false) MultipartFile file
        ) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            // 1) deserialize the incoming fields
            DemandeRecyclage incoming = mapper.readValue(demandeJson, DemandeRecyclage.class);

            // 2) fetch the current record from the DB
            DemandeRecyclage existing = demandeRecyclageService.findById(incoming.getIdDemandeRecyclage());

            // 3) copy all updatable properties
            existing.setDescriptionDemandeRecyclage(incoming.getDescriptionDemandeRecyclage());
            existing.setDateCreationDemandeRecyclage(incoming.getDateCreationDemandeRecyclage());
            existing.setNbrAppareils(incoming.getNbrAppareils());
            existing.setPrixDemandeRecyclage(incoming.getPrixDemandeRecyclage());
            existing.setEtatDemandeRecyclage(incoming.getEtatDemandeRecyclage());
            // … any other simple fields …

            // 4) only overwrite the blob if a new file was provided
            if (file != null && !file.isEmpty()) {
                try {
                    existing.setImageData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors du traitement de l'image", e);
                }
            }

            // 5) save and return
            return demandeRecyclageService.updateDemandeRecyclage(existing);
        }



        // Delete a DemandeRecyclage by ID
        @DeleteMapping("/remove/{id}")
        public void removeDemande(@PathVariable int id) {
            demandeRecyclageService.removeDemandeRecyclage(id);
        }
    }
