package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Repository.StockageManagement.IEspaceStockageRepository;
import tn.esprit.ecocycletech.Repository.StockageManagement.IFichierRepository;

import java.util.List;

@Service
public class EspaceStockageService implements IEspaceStockageService {

    @Autowired
    private IEspaceStockageRepository espaceStockageRepository;

    @Override
    public List<EspaceStockage> GetAllEspaceStockages() {
        return espaceStockageRepository.findAll();
    }

    @Override
    public EspaceStockage GetEspaceStockage(Long id) {
        return espaceStockageRepository.findByIdEspace(id);
    }

    @Override
    public EspaceStockage addEspaceStockage(EspaceStockage e) {
        return espaceStockageRepository.save(e);
    }

    @Override
    public EspaceStockage updateEspaceStockage(EspaceStockage e) {
        if (espaceStockageRepository.existsById(e.getIdEspace())) {
            return espaceStockageRepository.save(e);
        }
        return null;
    }

    @Override
    public void DeleteEspaceStockage(Long id) {
        if (espaceStockageRepository.existsById(id)) {
            espaceStockageRepository.deleteById(id);
        }
    }
}