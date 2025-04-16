package tn.esprit.ecocycletech.Service.StockageManagement;

import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;

import java.util.List;

public interface IFichierService {
    public List<Fichier> GetAllFichiers();
    public Fichier GetFichier(Long id);
    List<Fichier> addFichiers (List<Fichier> fichiers);
    Fichier addFichier (Fichier f);
    Fichier updateFichier (Fichier f);
    void DeleteFichier(Long id);
}
