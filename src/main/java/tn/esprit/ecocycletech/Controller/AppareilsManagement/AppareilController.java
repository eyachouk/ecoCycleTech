package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Avis;
import tn.esprit.ecocycletech.Service.AppareilsManagement.IAppareilService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appareils")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Content-Disposition", maxAge = 3600)
@Validated
public class AppareilController {

    @Autowired
    private IAppareilService appareilService;

    private final Path uploadPath = Paths.get("src/main/resources/static/images");
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        try {
            Path filePath = uploadPath.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
            }

            // Create upload directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate safe filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : ".png";
            String newFilename = UUID.randomUUID().toString() + extension; // Added .toString() here

            // Save file
            Path targetPath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Return relative path (not full URL)
            return ResponseEntity.ok(Map.of(
                    "imageUrl", newFilename
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload image: " + e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<List<Appareil>> getAllAppareils() {
        List<Appareil> appareils = appareilService.getAllAppareils();
        appareils.forEach(appareil -> {
            appareil.getAvis().forEach(avis -> {
                avis.setAppareil(null); // Remove bidirectional reference
            });
        });
        return ResponseEntity.ok(appareils);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appareil> getAppareilById(@PathVariable int id) {
        Appareil appareil = appareilService.getAppareilById(id);
        if (appareil != null) {
            return ResponseEntity.ok(appareil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Appareil> addAppareil(@RequestBody Appareil appareil) {
        Appareil saved = appareilService.saveAppareil(appareil);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appareil> updateAppareil(@PathVariable int id, @RequestBody Appareil appareil) {
        Appareil updatedAppareil = appareilService.updateAppareil(id, appareil);
        if (updatedAppareil != null) {
            return ResponseEntity.ok(updatedAppareil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppareil(@PathVariable int id) {
        try {
            appareilService.deleteAppareil(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("Cannot delete device as it is referenced by other entities");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting device: " + e.getMessage());
        }
    }
    @PostMapping("/predict-prix")
    public ResponseEntity<Double> predictPrix(@RequestBody Map<String, String> payload) {
        String description = payload.get("description");
        if (description == null || description.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String url = "http://localhost:5000/predict";

        Map<String, String> request = new HashMap<>();
        request.put("description", description);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();
            Double prixPred = Double.valueOf(body.get("prix_pred").toString());
            return ResponseEntity.ok(prixPred);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    }

