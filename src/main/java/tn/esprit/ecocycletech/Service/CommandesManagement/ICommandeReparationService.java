package tn.esprit.ecocycletech.Service.CommandesManagement;


import tn.esprit.ecocycletech.Entity.CommandesManagement.CommandeReparation;

import java.util.List;

public interface ICommandeReparationService {

    public CommandeReparation findById(Integer id);
    public List<CommandeReparation> findAll();
    public CommandeReparation save(CommandeReparation CommandeReparation);
    public CommandeReparation update(CommandeReparation CommandeReparation);
    public void delete(CommandeReparation CommandeReparation);
}
