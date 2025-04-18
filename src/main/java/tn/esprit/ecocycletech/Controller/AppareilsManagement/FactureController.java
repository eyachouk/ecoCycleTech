package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;
import tn.esprit.ecocycletech.Service.AppareilsManagement.FactureServiceImpl;

@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à accéder
@RestController
@RequestMapping("/api/factures") // Préfixe commun pour toutes les routes
public class FactureController {

    private final FactureServiceImpl factureService;

    public FactureController(FactureServiceImpl factureService) {
        this.factureService = factureService;
    }

    // Télécharger une facture PDF par ID
    @GetMapping("/download/{factureId}")
    public ResponseEntity<byte[]> downloadFacture(@PathVariable int factureId) {
        byte[] pdfBytes = factureService.generateAndDownloadFacturePdf(factureId).getBody();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture.pdf")
                .body(pdfBytes);
    }

    // Créer une facture à partir d'une réservation
    @PostMapping("/create/{reservationId}")
    public ResponseEntity<Facture> createFacture(@PathVariable int reservationId) {
        Facture facture = factureService.createFactureFromReservation(reservationId);
        return ResponseEntity.ok(facture);
    }
}
