package tn.esprit.ecocycletech.Service.AppareilsManagement;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IFactureRepository;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FactureServiceImpl implements IFactureService {

    private final IFactureRepository factureRepository;
    private final IReservationRepository reservationRepository;

    @Autowired
    public FactureServiceImpl(IFactureRepository factureRepository, IReservationRepository reservationRepository) {
        this.factureRepository = factureRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Facture createFactureForReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Obtenir l'heure actuelle
        LocalDateTime localDateTime = LocalDateTime.now();

        // Convertir LocalDateTime en Date
        Date factureDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Facture facture = new Facture();
        facture.setDate(factureDate);  // Utiliser java.util.Date
        facture.setReservation(reservation);

        return factureRepository.save(facture);
    }
    public ResponseEntity<byte[]> generateAndDownloadFacturePdf(int factureId) {
        try {
            Facture facture = factureRepository.findById(factureId)
                    .orElseThrow(() -> new RuntimeException("Facture not found"));

            byte[] pdfBytes = generateFacturePdf(facture);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture_" + factureId + ".pdf");
            headers.set(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur lors du téléchargement de la facture : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur dans generateAndDownloadFacturePdf", e);
        }
    }


    private byte[] generateFacturePdf(Facture facture) throws Exception {
        try {
            Reservation reservation = facture.getReservation();
            if (reservation == null) {
                throw new RuntimeException("Réservation liée à la facture est null.");
            }

            List<Appareil> appareils = reservation.getPanier();
            if (appareils == null || appareils.isEmpty()) {
                throw new RuntimeException("Panier vide ou null pour la réservation ID: " + reservation.getIdReservation());
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Facture EcocycleTech")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Facture N°: " + facture.getIdFacture()));
            document.add(new Paragraph("Date: " + facture.getDate()));
            document.add(new Paragraph("Reservation ID: " + reservation.getIdReservation()));

            double total = 0;
            document.add(new Paragraph("Appareils:"));
            for (Appareil appareil : appareils) {
                document.add(new Paragraph("- " + appareil.getNom() + " - " + appareil.getPrix() + " DT"));
                total += appareil.getPrix();
            }

            document.add(new Paragraph("Total payé: " + total + " DT"));
            document.add(new Paragraph("Merci pour votre achat !"));
            document.add(new Paragraph("EcoCycleTech - www.ecocycletech.com"));

            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            System.err.println("Error during facture generation: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error generating facture", e);
        }
    }
    public Facture createFactureFromReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        Facture facture = new Facture();

        // Vérifier si la date de réservation est nulle
        if (reservation.getDate() == null) {
            throw new RuntimeException("La date de réservation est nulle");
        }

        // Convertir la date de réservation en java.util.Date
        // If it's a java.sql.Date, use reservation.getDate().toLocalDate() first
        Date reservationDate = new Date(reservation.getDate().getTime());

        facture.setDate(reservationDate);  // Setting the date for the facture

        facture.setReservation(reservation);

        return factureRepository.save(facture);
    }


}
