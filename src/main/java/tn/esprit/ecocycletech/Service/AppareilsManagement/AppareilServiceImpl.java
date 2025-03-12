package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.AppareilsManagement.Appareil;
import tn.esprit.ecocycletech.Repository.AppareilsManagement.IAppareilRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppareilServiceImpl implements IAppareilService {

    @Autowired
    private IAppareilRepository appareilRepository;
    @Override
    public Appareil getAppareilById(int id) {
        return appareilRepository.findById(id).orElse(null);
    }
    @Override
    public Appareil saveAppareil(Appareil appareil) {
        return appareilRepository.save(appareil);
    }

    @Override
    public boolean deleteAppareil(int id) {
        appareilRepository.deleteById(id);
        return false;
    }

    // Récupérer tous les appareils
    @Override
    public List<Appareil> getAllAppareils() {
        return appareilRepository.findAll();
    }

    // Mettre à jour un appareil existant
    @Override
    public Appareil updateAppareil(int id, Appareil appareil) {
        Optional<Appareil> existingAppareilOpt = appareilRepository.findById(id);
        if (existingAppareilOpt.isPresent()) {
            Appareil existingAppareil = existingAppareilOpt.get();
            existingAppareil.setNom(appareil.getNom());
            existingAppareil.setCategorie(appareil.getCategorie());
            existingAppareil.setEtatAppareil(appareil.getEtatAppareil());
            existingAppareil.setMarque(appareil.getMarque());
            existingAppareil.setQuantite(appareil.getQuantite());
            existingAppareil.setPrix(appareil.getPrix());
            existingAppareil.setDescription(appareil.getDescription());
            existingAppareil.setImageurl(appareil.getImageurl());

            return appareilRepository.save(existingAppareil);
        }
        return null;
}}
