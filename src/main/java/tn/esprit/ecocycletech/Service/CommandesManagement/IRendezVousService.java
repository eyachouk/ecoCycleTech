package tn.esprit.ecocycletech.Service.CommandesManagement;

import tn.esprit.ecocycletech.Entity.CommandesManagement.RendezVous;

import java.util.List;

public interface IRendezVousService {
    public RendezVous findById(Integer id);
    public List<RendezVous> findAll();
    public RendezVous save(RendezVous rendezVous);
    public RendezVous update(RendezVous rendezVous);
    public void delete(RendezVous rendezVous);
}
