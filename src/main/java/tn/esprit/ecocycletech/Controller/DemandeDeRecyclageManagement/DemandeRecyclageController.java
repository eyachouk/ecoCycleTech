package tn.esprit.ecocycletech.Controller.DemandeDeRecyclageManagement;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.CertificatRecyclage;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclage;
import tn.esprit.ecocycletech.Entity.DemandeDeRecyclageManagement.DemandeRecyclageDTO;
import tn.esprit.ecocycletech.Entity.Enumerations.Etat;
import tn.esprit.ecocycletech.Service.DemandeDeRecyclageManagement.IDemandeRecyclageService;
import tn.esprit.ecocycletech.Repository.DemandeDeRecyclageManagement.ICertificatRecyclageRepository;
import tn.esprit.ecocycletech.Service.Notifications.EmailService;
import tn.esprit.ecocycletech.Service.Notifications.SmsService;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping("/demandeRecyclage")
@AllArgsConstructor
public class DemandeRecyclageController {

    @Autowired
    IDemandeRecyclageService demandeRecyclageService;
    @Autowired
    private ICertificatRecyclageRepository certificatRecyclageRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    SmsService smsService;

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
        String body = String.format(
                "Bonjour,%n%n" +
                        "Nous avons bien reçu votre demande de recyclage n°%1$s, déposée maintenant avec la date de fabrication de votre produit est le %2$td/%2$tm/%2$tY.%n%n" +
                        "→ Description : %3$s%n" +
                        "→ Nombre d’appareils : %4$d%n%n" +
                        "Nous vous tiendrons informé(e) dès qu’elle sera traitée.%n%n" +
                        "Merci de votre confiance,%n" +
                        "L’équipe EcoCycleTech",
                //  1         2                                    3                          4
                demande.getTitle(),              // %1$s
                demande.getDateCreationDemandeRecyclage(),    // %2$td / %2$tm / %2$tY
                demande.getDescriptionDemandeRecyclage(),     // %3$s
                demande.getNbrAppareils()                     // %4$d
        );

        //    emailService.sendSimpleMessage("aminshil54@gmail.com","Votre demande de recyclage est créée",body);

        //    smsService.sendSms("+21629131606","Votre demande de recyclage est créée avec succéesQ");
        System.out.println("sms sent");

        return demandeRecyclageService.addDemandeRecyclage(demande);

    }




    // Get all DemandeRecyclage
    @GetMapping("/all")
    public ResponseEntity<List<DemandeRecyclageDTO>> getAllDemandes() {
        List<DemandeRecyclage> demandes = demandeRecyclageService.findAll();
        List<DemandeRecyclageDTO> dtos = demandes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private DemandeRecyclageDTO convertToDTO(DemandeRecyclage demande) {
        DemandeRecyclageDTO dto = new DemandeRecyclageDTO();
        dto.setIdDemandeRecyclage(demande.getIdDemandeRecyclage());
        dto.setTitle(demande.getTitle());
        dto.setDateCreationDemandeRecyclage(demande.getDateCreationDemandeRecyclage());
        dto.setDescriptionDemandeRecyclage(demande.getDescriptionDemandeRecyclage());
        dto.setEtatDemandeRecyclage(demande.getEtatDemandeRecyclage());
        dto.setNbrAppareils(demande.getNbrAppareils());
        dto.setPrixDemandeRecyclage(demande.getPrixDemandeRecyclage());
        //dto.setImageData(demande.getImageData());
        return dto;
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
    @Transactional
    public DemandeRecyclage updateDemande(
            @RequestPart("demande") String demandeJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        // 1) deserialize and load
        DemandeRecyclage incoming = new ObjectMapper().readValue(demandeJson, DemandeRecyclage.class);
        DemandeRecyclage existing = demandeRecyclageService.findById(incoming.getIdDemandeRecyclage());

        boolean wasTraite    = existing.getEtatDemandeRecyclage() == Etat.TRAITE;
        boolean willBeTraite = incoming.getEtatDemandeRecyclage() == Etat.TRAITE;

        // 2) if flipping *out* of TRAITE, break the link and delete the cert
        if (wasTraite && !willBeTraite) {
            certificatRecyclageRepository
                    .findByDemandeRecyclage_IdDemandeRecyclage(existing.getIdDemandeRecyclage())
                    .ifPresent(cert -> {
                        // detach from the demande so Hibernate won’t try to re-merge it
                        existing.setCertificatRecyclage(null);
                        // now delete it
                        certificatRecyclageRepository.deleteById(cert.getIdCertificatRecyclage());
                    });
        }

        // 3) copy all the updatable fields
        existing.setDescriptionDemandeRecyclage(incoming.getDescriptionDemandeRecyclage());
        existing.setDateCreationDemandeRecyclage(incoming.getDateCreationDemandeRecyclage());
        existing.setNbrAppareils(incoming.getNbrAppareils());
        existing.setPrixDemandeRecyclage(incoming.getPrixDemandeRecyclage());
        existing.setEtatDemandeRecyclage(incoming.getEtatDemandeRecyclage());
        if (file != null && !file.isEmpty()) {
            existing.setImageData(file.getBytes());
        }

        // 4) save the demande (no more cascade to the old cert!)
        DemandeRecyclage saved = demandeRecyclageService.updateDemandeRecyclage(existing);

        // 5) if flipping *into* TRAITE, emit a brand-new certificate
        if (!wasTraite && willBeTraite) {
            CertificatRecyclage cert = new CertificatRecyclage();
            cert.setDateEmissionCertificat(new Date()); // includes hours/min/sec
            cert.setDemandeRecyclage(saved);
            certificatRecyclageRepository.save(cert);
            emailService.sendSimpleMessage(
                    "aminshil54@gmail.com",
                    "Votre certificat de recyclage est prêt",
                    "Bonjour,\n\n" +
                            "Votre certificat (ID=" + cert.getIdCertificatRecyclage() +
                            ") pour la demande n°" + cert.getDemandeRecyclage().getIdDemandeRecyclage() +
                            " est désormais disponible.\n\n" +
                            "Merci de votre confiance,\nEcoCycleTech"
            );
        }

        return saved;
    }

    // Delete a DemandeRecyclage by ID
    @DeleteMapping("/remove/{id}")
    public void removeDemande(@PathVariable int id) {
        demandeRecyclageService.removeDemandeRecyclage(id);
    }





}
