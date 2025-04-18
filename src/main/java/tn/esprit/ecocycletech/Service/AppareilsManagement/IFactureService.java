package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Facture;


public interface IFactureService {

     Facture createFactureForReservation(int reservationId) ;
     ResponseEntity<byte[]> generateAndDownloadFacturePdf(int factureId) throws Exception ;

}
