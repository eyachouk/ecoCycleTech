package tn.esprit.ecocycletech.Service.EvenementsManagement;

import tn.esprit.ecocycletech.Entity.EvenementsManagement.Evenement;

import java.util.List;

public interface IEvenementService {
    public Evenement save(Evenement event);
    public Evenement findById(int id);
    public void delete(int id);
    public  Evenement update(Evenement event);
    public Evenement add(Evenement event);
    List<Evenement> retrieveAllEvenements();
    List<Evenement> getEventsSortedByDateDesc();
    List<Evenement> getEventsSortedByDateAsc();
    List<Evenement> getTodayEvents();
    List<Evenement> getUpcomingEvents();
    List<Evenement> getPastEvents();
}
