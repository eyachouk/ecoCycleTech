package tn.esprit.ecocycletech.Repository.StockageManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ecocycletech.Entity.StockageManagement.EspaceStockage;
import tn.esprit.ecocycletech.Entity.StockageManagement.Fichier;

@Repository
public interface IEspaceStockageRepository extends JpaRepository<EspaceStockage,Long> {
    public EspaceStockage findByIdEspace(Long id);

}
