package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;
import tn.esprit.ecocycletech.Repository.StockageManagement.IFichierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FichierService implements IFichierService {


    @Autowired
    private IFichierRepository fichierRepository;

    @Override
    public List<Fichier> GetAllFichiers() {
        return fichierRepository.findAll();
    }

    @Override
    public Fichier GetFichier(Long id) {
        return fichierRepository.findByIdFichier(id);
    }

    @Override
    public List<Fichier> addFichiers(List<Fichier> fichiers) {
        return fichierRepository.saveAll(fichiers);
    }

    @Override
    public Fichier addFichier(Fichier fichier) {
        return fichierRepository.save(fichier);
    }
    @Override
    public Fichier updateFichier(Fichier f) {
        if (fichierRepository.findByIdFichier(f.getIdFichier()) != null) {
        return fichierRepository.save(f);}
        return null;
    }

    @Override
    public void DeleteFichier(Long id) {
        fichierRepository.deleteById(id);
    }


    @Override
    public Fichier GetFichierByPublicId(String publicId){
        return fichierRepository.findByCloudinaryPublicId(publicId);
    }
}
