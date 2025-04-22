package tn.esprit.ecocycletech.Controller.StockageManagement;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.esprit.ecocycletech.Service.StockageManagement.PaypalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
    private static final Logger log = LoggerFactory.getLogger(PaypalController.class);

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Payment service is running");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description
    ) {
        try {
            String cancelUrl = "http://localhost:8090/api/payment/cancel";
            String successUrl = "http://localhost:8090/api/payment/success";

            Payment payment = paypalService.createPayment(
                    Double.valueOf(amount),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl
            );

            Map<String, String> response = new HashMap<>();

            for (Links links : payment.getLinks()) {
                response.put(links.getRel(), links.getHref());
            }

            // Include payment ID for later use in execute payment
            response.put("paymentId", payment.getId());

            return ResponseEntity.ok(response);
        } catch (PayPalRESTException e) {
            log.error("Error occurred during payment creation: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/execute")
    public ResponseEntity<?> executePayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", payment.getState());
            response.put("paymentId", payment.getId());
            response.put("paymentDetails", payment.toJSON());

            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(response);
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred during payment execution: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<Map<String, String>> paymentCancel() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "CANCELLED");
        response.put("message", "Payment was cancelled");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/error")
    public ResponseEntity<Map<String, String>> paymentError() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", "An error occurred during payment processing");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}