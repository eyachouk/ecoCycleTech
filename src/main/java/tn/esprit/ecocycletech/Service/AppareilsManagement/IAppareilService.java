package tn.esprit.ecocycletech.Service.AppareilsManagement;

import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;

import java.util.List;

public interface IAppareilService {
    List<Appareil> getAllAppareils();
    public Appareil getAppareilById(int id);
    public boolean deleteAppareil(int id);
    public Appareil saveAppareil(Appareil appareil);
    public Appareil updateAppareil(int id, Appareil appareil);
    public double calculerNoteMoyenne(Appareil appareil);
}
