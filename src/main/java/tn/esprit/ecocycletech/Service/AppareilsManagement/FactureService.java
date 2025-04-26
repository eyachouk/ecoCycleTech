package tn.esprit.ecocycletech.Service.AppareilsManagement;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IFactureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IReservationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class FactureService implements IFactureService {
    private final  IFactureRepository IFactureRepository;
    private final  IReservationRepository reservationRepository;
    private final  PdfService pdfService;
    private  final GoogleDriveService googleDriveService;

    public FactureService(tn.esprit.ecocycletech.Repository.AppareilsManagement.IFactureRepository IFactureRepository, GoogleDriveService googleDriveService, IReservationRepository reservationRepository, PdfService pdfService) {
        this.IFactureRepository = IFactureRepository;
        this.googleDriveService = googleDriveService;
        this.reservationRepository = reservationRepository;
        this.pdfService = pdfService;
    }

    public Facture createFactureForReservation(int reservationId) throws DocumentException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Generate PDF
        byte[] pdfContent = pdfService.generateInvoicePdf(reservation);

        try {
            // Upload to Google Drive
            String fileName = "Facture_" + reservationId + "_" + UUID.randomUUID() + ".pdf";
            String pdfUrl = googleDriveService.uploadPdf(pdfContent, fileName);

            Facture facture = new Facture();
            facture.setReservation(reservation);
            facture.setMontant(reservation.getTotal());
            facture.setDateFacture(LocalDateTime.now());
            facture.setPdfUrl(pdfUrl);

            return IFactureRepository.save(facture);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create facture", e);
        }
    }

    public Facture getFactureByReservationId(int reservationId) {
        return IFactureRepository.findByReservationIdReservation(reservationId);
    }

    public byte[] generatePdf(Long factureId) throws DocumentException {
        Facture facture = IFactureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture not found"));
        return pdfService.generateInvoicePdf(facture.getReservation());
    }
}