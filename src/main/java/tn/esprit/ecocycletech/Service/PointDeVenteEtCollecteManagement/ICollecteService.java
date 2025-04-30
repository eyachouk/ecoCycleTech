package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.Collecte;
import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointCollecte;

import java.time.LocalTime;
import java.util.List;

public interface ICollecteService {
    public Collecte findById(Integer id);
    public List<Collecte> findAll();
    public Collecte save(Collecte collecte);
    public Collecte update(Collecte collecte);
    public void delete(Collecte collecte);
    public Collecte planCollecte(Integer demandeId, Integer pointCollecteId);

}
