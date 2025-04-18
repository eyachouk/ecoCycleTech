package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Service.AppareilsManagement.StripeService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:4200")

public class StripeController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestParam Long reservationId,
            @RequestParam Double amount) {
        System.out.println(">>> Création session Stripe pour réservation: " + reservationId + ", montant: " + amount);

        try {
            String sessionUrl = stripeService.createCheckoutSession(reservationId, amount);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("url", sessionUrl);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            e.printStackTrace(); // ⛔️ Imprime l'erreur dans la console
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
