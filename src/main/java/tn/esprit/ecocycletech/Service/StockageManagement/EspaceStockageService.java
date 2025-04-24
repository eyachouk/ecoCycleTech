package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.PlanStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.StatutEspace;
import tn.esprit.ecocycletech.Entity.StockageManagement.Subscription;
import tn.esprit.ecocycletech.Repository.StockageManagement.IEspaceStockageRepository;
import tn.esprit.ecocycletech.Repository.StockageManagement.IFichierRepository;
import tn.esprit.ecocycletech.Repository.StockageManagement.IPlanStockageRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EspaceStockageService implements IEspaceStockageService {

    @Autowired
    private IEspaceStockageRepository espaceStockageRepository;
    @Autowired
    private IPlanStockageRepository psRepo;

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
        if (espaceStockageRepository.findByIdEspace(e.getIdEspace()) != null) {
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

    @Override
    public List<EspaceStockage> GetEspaceStockageByPlan(long planId) {
       PlanStockage p = this.psRepo.findByIdPlan(planId);

        if (p != null) {
            List<Subscription> subscriptionList= p.getSubscriptions();

            List<EspaceStockage> espaces = subscriptionList.stream()
                    .map(Subscription::getEspace)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return espaces;
        }
        return null;
    }


    @Override
    public EspaceStockage blockEspaceStockage(EspaceStockage e) {
        e.setStatut(StatutEspace.Blocked);
        espaceStockageRepository.save(e);
        return e;
    }
    @Override
    public EspaceStockage unblockEspaceStockage(EspaceStockage e) {
        Date now = new Date();
       if (e.getDateExpiration().compareTo(now) > 0){
           e.setStatut(StatutEspace.Active);
       }
       else {
           e.setStatut(StatutEspace.Expired);
       }
        espaceStockageRepository.save(e);
        return e;
    }
}