package tn.esprit.ecocycletech.Service.AppareilsManagement;

import com.lowagie.text.DocumentException;
import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;


public interface IFactureService {

     Facture createFactureForReservation(int reservationId) throws DocumentException;

}
