package tn.esprit.ecocycletech.Service.AppareilsManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Reservation;

import java.util.List;

public interface IReservationService {
    public List<Reservation> getAllReservations();
    public Reservation getReservationById(int id);
    public Reservation saveReservation(Reservation reservation);
    public void deleteReservation(int id);
}

