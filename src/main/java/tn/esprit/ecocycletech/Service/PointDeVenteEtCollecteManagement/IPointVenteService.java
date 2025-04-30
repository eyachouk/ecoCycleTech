package tn.esprit.ecocycletech.Service.PointDeVenteEtCollecteManagement;

import tn.esprit.ecocycletech.Entity.PointsDeVenteEtCollecteManagement.PointVente;

import java.util.List;

public interface IPointVenteService {
    public PointVente findById(Integer id);
    public List<PointVente> findAll();
    public PointVente save(PointVente pointVente);
    PointVente update(PointVente pointVente);
    public void delete(PointVente pointVente);
}
