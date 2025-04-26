package tn.esprit.ecocycletech.Service.StockageManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.StatutEspace;
import tn.esprit.ecocycletech.Repository.StockageManagement.IEspaceStockageRepository;

import java.util.Date;
import java.util.List;

@Service
public class ExpirationCheckerService {

    @Autowired
    private IEspaceStockageRepository espaceRepo;

    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Paris")
    public void checkAndExpireSpaces() {
        System.out.println("Scheduled task running: " + new Date());
        Date now = new Date();
        // Debug logging
        System.out.println("Current time: " + now);

        List<EspaceStockage> expiredSpaces = espaceRepo.findByDateExpirationBeforeOrEqualAndStatutNot(now, StatutEspace.Expired);
        System.out.println("Found expired spaces: " + expiredSpaces.size());

        for (EspaceStockage espace : expiredSpaces) {
            espace.setStatut(StatutEspace.Expired);
        }

        espaceRepo.saveAll(expiredSpaces);
    }
}
