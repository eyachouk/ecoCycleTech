package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import com.lowagie.text.DocumentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;
import tn.esprit.ecocycletech.Service.AppareilsManagement.FactureService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/factures")
public class FactureController {
    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/create/{reservationId}")
    public ResponseEntity<Facture> createFacture(@PathVariable int reservationId) throws DocumentException {
        Facture facture = factureService.createFactureForReservation(reservationId);
        return ResponseEntity.ok(facture);
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity<Facture> getByReservation(@PathVariable int reservationId) {
        Facture facture = factureService.getFactureByReservationId(reservationId);
        return facture != null ? ResponseEntity.ok(facture) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{factureId}/download")
    public ResponseEntity<byte[]> downloadFacture(@PathVariable Long factureId) throws DocumentException {
        byte[] pdfContent = factureService.generatePdf(factureId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }
}