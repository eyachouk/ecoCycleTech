package tn.esprit.ecocycletech.Service.AppareilsManagement;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    public byte[] generateInvoicePdf(Reservation reservation) throws DocumentException {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        try {
            // Format date
            String formattedDate = "N/A";
            if (reservation.getDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                formattedDate = dateFormat.format(reservation.getDate());
            }

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Facture #" + reservation.getIdReservation(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Client info
            String clientName =reservation.getUser().getUsername();
            if (reservation.getUser().getUsername() != null) {
                clientName = (reservation.getUser().getNom() != null ? reservation.getUser().getNom() : "")
                        + " "
                        + (reservation.getUser().getPrenom() != null ? reservation.getUser().getPrenom() : "");
            }
            document.add(new Paragraph("Client: " + clientName.trim()));
            document.add(new Paragraph("Date: " + formattedDate));
            document.add(new Paragraph("Total: " + reservation.getTotal() + " TND"));

            // Items table
            if (reservation.getPanier() != null && !reservation.getPanier().isEmpty()) {
                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.addCell("Appareil");
                table.addCell("Prix");
                table.addCell("Quantité");

                for (Appareil item : reservation.getPanier()) {
                    table.addCell(item.getNom() != null ? item.getNom() : "N/A");
                    table.addCell(item.getPrix() + " TND");
                    table.addCell("1"); // Assuming quantity is always 1
                }
                document.add(table);
            } else {
                document.add(new Paragraph("Aucun appareil dans le panier"));
            }

        } finally {
            document.close();
        }
        return outputStream.toByteArray();
    }
}