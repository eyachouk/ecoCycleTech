package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IAppareilRepository;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IAppareilRepository appareilRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        // Handle the appareils
        if (reservation.getPanier() != null) {
            // Create a new list to avoid modifying the original during iteration
            List<Appareil> appareils = new ArrayList<>(reservation.getPanier());

            // Clear the existing panier to rebuild it properly
            reservation.getPanier().clear();

            // Add each appareil properly
            for (Appareil appareil : appareils) {
                Appareil managedAppareil = appareilRepository.findById(appareil.getIdAppareil())
                        .orElseThrow(() -> new RuntimeException("Appareil not found with id: " + appareil.getIdAppareil()));

                // This will handle both sides of the relationship
                reservation.addAppareil(managedAppareil);
            }
        }
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(int id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Clear relationships before deletion
        if (reservation.getPanier() != null) {
            new ArrayList<>(reservation.getPanier()).forEach(appareil -> {
                appareil.setReservation(null);
                appareilRepository.save(appareil);
            });
        }

        reservationRepository.delete(reservation);
    }

    public void addAppareilToReservation(int reservationId, int appareilId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Appareil appareil = appareilRepository.findById(appareilId)
                .orElseThrow(() -> new RuntimeException("Appareil not found"));

        // This handles both sides of the relationship
        reservation.addAppareil(appareil);

        // Save both to ensure consistency
        reservationRepository.save(reservation);
        appareilRepository.save(appareil);
    }


    public Reservation createReservationWithAppareils(Reservation reservation, List<Integer> appareilIds) {
        // First save the reservation to get an ID
        Reservation savedReservation = reservationRepository.save(reservation);

        // Add each appareil to the reservation
        appareilIds.forEach(id -> {
            Appareil appareil = appareilRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Appareil not found with id: " + id));

            // This handles both sides of the relationship
            savedReservation.addAppareil(appareil);
            appareilRepository.save(appareil);
        });

        return reservationRepository.save(savedReservation);
    }
}