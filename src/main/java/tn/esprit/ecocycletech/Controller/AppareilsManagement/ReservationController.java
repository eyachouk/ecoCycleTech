package tn.esprit.ecocycletech.Controller.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import tn.esprit.ecocycletech.Entity.UserManagement.User;
import tn.esprit.ecocycletech.Service.AppareilsManagement.IReservationService;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecocycletech.Service.UserManagement.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/users")
    public List<User> getUsersForReservation() {
        return userServiceImpl.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable int id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable int id) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            reservationService.deleteReservation(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable int id, @RequestBody Reservation reservation) {
        Reservation existingReservation = reservationService.getReservationById(id);
        if (existingReservation != null) {
            existingReservation.setStatut(reservation.getStatut());
            existingReservation.setTotal(reservation.getTotal());
            existingReservation.setDate(reservation.getDate());
            existingReservation.setUser(reservation.getUser());
            existingReservation.setPanier(reservation.getPanier());

            Reservation updatedReservation = reservationService.saveReservation(existingReservation);
            return ResponseEntity.ok(updatedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
